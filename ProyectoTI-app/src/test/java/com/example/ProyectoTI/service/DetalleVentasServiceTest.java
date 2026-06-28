package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.DTO.DetalleVentaDTO;
import com.example.ProyectoTI.model.DetalleVenta;
import com.example.ProyectoTI.model.Producto;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.repository.DetalleVentaRepository;
import com.example.ProyectoTI.repository.ProductoRepository;
import com.example.ProyectoTI.repository.VentaRepository;

import net.datafaker.Faker;

@SpringBootTest
public class DetalleVentasServiceTest {

    @Autowired
    private DetalleVentasService detalleVentasService;

    @MockitoBean
    private DetalleVentaRepository detalleVentaRepository;

    @MockitoBean
    private VentaRepository ventaRepository;

    @MockitoBean
    private ProductoRepository productoRepository;

    private static final Faker faker = new Faker();

    private Producto createProducto(){
        return new Producto(1,
            faker.commerce().productName(),
            faker.lorem().sentence(),
            10,
            null,
            faker.number().randomDouble(2, 1000, 20000),
            null,
            null
        );
    }

    private DetalleVenta createDetalle(){
        DetalleVenta detalle = new DetalleVenta();
        detalle.setIdDetalleVenta(1);
        Producto producto = createProducto();
        detalle.setProducto(producto);
        detalle.setCantidadProducto(faker.number().numberBetween(1, 10));
        detalle.setPrecioUnitario(producto.getPrecioProducto());
        return detalle;
    }

    @Test
    public void testObtenerTodos(){
        when(detalleVentaRepository.findAll()).thenReturn(List.of(createDetalle()));
        List<DetalleVenta> detalles = detalleVentasService.obtenerTodos();
        assertNotNull(detalles);
        assertEquals(1, detalles.size());
    }

    @Test
    public void testObtenerPorId(){
        DetalleVenta detalleEsperado = createDetalle();
        when(detalleVentaRepository.findById(1)).thenReturn(Optional.of(detalleEsperado));
        DetalleVenta detalle = detalleVentasService.obtenerPorId(1);
        assertNotNull(detalle);
        assertEquals(detalleEsperado.getCantidadProducto(), detalle.getCantidadProducto());
    }

    @Test
    public void testObtenerPorDetalleVenta(){
        when(detalleVentaRepository.findByVenta_IdVenta(1)).thenReturn(List.of(createDetalle()));
        List<DetalleVenta> detalles = detalleVentasService.obtenerPorDetalleVenta(1);
        assertNotNull(detalles);
        assertEquals(1, detalles.size());
    }

    @Test
    public void testGuardarDetalle(){
        Producto producto = createProducto();
        int cantidad = faker.number().numberBetween(1, 10);

        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setIdVenta(1);
        dto.setIdProducto(1);
        dto.setCantidadProducto(cantidad);
        dto.setPrecioUnitario(producto.getPrecioProducto());

        DetalleVenta detalleEsperado = new DetalleVenta();
        detalleEsperado.setIdDetalleVenta(1);
        detalleEsperado.setProducto(producto);
        detalleEsperado.setCantidadProducto(cantidad);
        detalleEsperado.setPrecioUnitario(producto.getPrecioProducto());

        when(ventaRepository.findById(1)).thenReturn(Optional.of(new Venta()));
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(detalleEsperado);

        DetalleVenta guardado = detalleVentasService.guardarDetalle(dto);
        assertNotNull(guardado);
        assertEquals(cantidad, guardado.getCantidadProducto());
    }

    @Test
    public void testProcesarVenta_stockSuficiente(){
        Venta venta = new Venta();
        venta.setIdVenta(1);
        Producto producto = createProducto();
        int cantidad = faker.number().numberBetween(1, 5); 

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidadProducto(cantidad);

        when(ventaRepository.findById(1)).thenReturn(Optional.of(venta));
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepository.save(producto)).thenReturn(producto);
        when(detalleVentaRepository.save(detalle)).thenReturn(detalle);
        when(ventaRepository.save(venta)).thenReturn(venta);

        Venta resultado = detalleVentasService.procesarVenta(1, List.of(detalle));
        assertNotNull(resultado);
        assertEquals(producto.getPrecioProducto() * cantidad, resultado.getPrecioFinal());
    }

    @Test
    public void testProcesarVenta_sinStock(){
        Venta venta = new Venta();
        venta.setIdVenta(1);
        Producto producto = createProducto();
        producto.setStockProducto(0);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidadProducto(1);

        when(ventaRepository.findById(1)).thenReturn(Optional.of(venta));
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        assertThrows(RuntimeException.class, () -> detalleVentasService.procesarVenta(1, List.of(detalle)));
    }

    @Test
    public void testEliminarDetalle(){
        when(detalleVentaRepository.existsById(1)).thenReturn(true);
        String resultado = detalleVentasService.eliminarDetalle(1);
        assertEquals("Detalle de venta eliminado correctamente", resultado);
    }

}