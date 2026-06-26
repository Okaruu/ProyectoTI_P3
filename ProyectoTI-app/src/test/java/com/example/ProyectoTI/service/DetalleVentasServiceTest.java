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

    private Producto createProducto(){
        return new Producto(1, "Mouse", "Mouse inalámbrico", 10, null, 8000.0, null, null);
    }

    private DetalleVenta createDetalle(){
        DetalleVenta detalle = new DetalleVenta();
        detalle.setIdDetalleVenta(1);
        detalle.setProducto(createProducto());
        detalle.setCantidadProducto(2);
        detalle.setPrecioUnitario(8000.0);
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
        when(detalleVentaRepository.findById(1)).thenReturn(Optional.of(createDetalle()));
        DetalleVenta detalle = detalleVentasService.obtenerPorId(1);
        assertNotNull(detalle);
        assertEquals(2, detalle.getCantidadProducto());
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
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setIdVenta(1);
        dto.setIdProducto(1);
        dto.setCantidadProducto(2);
        dto.setPrecioUnitario(8000.0);

        when(ventaRepository.findById(1)).thenReturn(Optional.of(new Venta()));
        when(productoRepository.findById(1)).thenReturn(Optional.of(createProducto()));
        when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(createDetalle());

        DetalleVenta guardado = detalleVentasService.guardarDetalle(dto);
        assertNotNull(guardado);
        assertEquals(2, guardado.getCantidadProducto());
    }

    @Test
    public void testProcesarVenta_stockSuficiente(){
        Venta venta = new Venta();
        venta.setIdVenta(1);
        Producto producto = createProducto();

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidadProducto(2);

        when(ventaRepository.findById(1)).thenReturn(Optional.of(venta));
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepository.save(producto)).thenReturn(producto);
        when(detalleVentaRepository.save(detalle)).thenReturn(detalle);
        when(ventaRepository.save(venta)).thenReturn(venta);

        Venta resultado = detalleVentasService.procesarVenta(1, List.of(detalle));
        assertNotNull(resultado);
        assertEquals(16000.0, resultado.getPrecioFinal());
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