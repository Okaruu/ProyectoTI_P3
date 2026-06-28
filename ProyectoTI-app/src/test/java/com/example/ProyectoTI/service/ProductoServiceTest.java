package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.DTO.ProductoDTO;
import com.example.ProyectoTI.model.Categoria;
import com.example.ProyectoTI.model.Marca;
import com.example.ProyectoTI.model.Producto;
import com.example.ProyectoTI.repository.CategoriaRepository;
import com.example.ProyectoTI.repository.MarcaRepository;
import com.example.ProyectoTI.repository.ProductoRepository;

import net.datafaker.Faker;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockitoBean
    private ProductoRepository productoRepository;

    @MockitoBean
    private CategoriaRepository categoriaRepository;

    @MockitoBean
    private MarcaRepository marcaRepository;

    private static final Faker faker = new Faker();

    private Categoria createCategoria(){
        return new Categoria(1, faker.commerce().department(), faker.lorem().sentence());
    }

    private Marca createMarca(){
        return new Marca(1, faker.company().name());
    }
    private Producto createProducto(){
        return new Producto(1,
            faker.commerce().productName(),
            faker.lorem().sentence(),
            10,
            null,
            faker.number().randomDouble(2, 10000, 1000000),
            createCategoria(),
            createMarca()
        );
    }

    @Test
    public void testObtenerTodos(){
        Producto producto = createProducto();
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<ProductoDTO> productos = productoService.obtenerTodos();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals(producto.getNombreProducto(), productos.get(0).getNombreProducto());
    }

    @Test
    public void testObtenerPorId(){
        Producto productoEsperado = createProducto();
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEsperado));
        ProductoDTO producto = productoService.obtenerPorId(1);
        assertNotNull(producto);
        assertEquals(productoEsperado.getNombreProducto(), producto.getNombreProducto());
    }

    @Test
    public void testGuardarProducto(){
        String nombre = faker.commerce().productName();
        Producto productoGuardado = createProducto();
        productoGuardado.setNombreProducto(nombre);

        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto(nombre);
        dto.setDescripcionProducto(faker.lorem().sentence());
        dto.setPrecioProducto(productoGuardado.getPrecioProducto());
        dto.setIdCategoria(1);
        dto.setIdMarca(1);

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(createCategoria()));
        when(marcaRepository.findById(1)).thenReturn(Optional.of(createMarca()));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        Producto guardado = productoService.guardarProducto(dto);
        assertNotNull(guardado);
        assertEquals(nombre, guardado.getNombreProducto());
    }

    @Test
    public void testActualizarProducto(){
        Producto existente = createProducto();
        String nuevoNombre = faker.commerce().productName();

        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto(nuevoNombre);

        when(productoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(productoRepository.save(existente)).thenReturn(existente);

        Producto actualizado = productoService.actualizarProducto(1, dto);
        assertNotNull(actualizado);
        assertEquals(nuevoNombre, actualizado.getNombreProducto());
    }

    @Test
    public void testObtenerProductosPorCategoria(){
        when(productoRepository.buscarPorCategoria(1)).thenReturn(List.of(createProducto()));
        List<ProductoDTO> productos = productoService.obtenerProductosPorCategoria(1);
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testObtenerProductosPorMarca(){
        when(productoRepository.buscarPorMarca(1)).thenReturn(List.of(createProducto()));
        List<ProductoDTO> productos = productoService.obtenerProductosPorMarca(1);
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testEliminarProducto(){
        when(productoRepository.existsById(1)).thenReturn(true);
        String resultado = productoService.eliminarProducto(1);
        assertEquals("Producto eliminado correctamente", resultado);
    }

}