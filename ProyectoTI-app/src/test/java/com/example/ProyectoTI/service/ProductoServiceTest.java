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

    private Categoria createCategoria(){
        return new Categoria(1, "Electrónica", "Productos electrónicos");
    }

    private Marca createMarca(){
        return new Marca(1, "Samsung");
    }

    // ¡cuidado! convertirADTO() llama a producto.getCategoria() y .getMarca() sin null-check,
    // así que en los tests siempre hay que pasarlos no nulos o lanzará NullPointerException
    private Producto createProducto(){
        return new Producto(1, "Notebook", "Notebook 15 pulgadas", 10, null, 500000.0, createCategoria(), createMarca());
    }

    @Test
    public void testObtenerTodos(){
        when(productoRepository.findAll()).thenReturn(List.of(createProducto()));
        List<ProductoDTO> productos = productoService.obtenerTodos();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Notebook", productos.get(0).getNombreProducto());
    }

    @Test
    public void testObtenerPorId(){
        when(productoRepository.findById(1)).thenReturn(Optional.of(createProducto()));
        ProductoDTO producto = productoService.obtenerPorId(1);
        assertNotNull(producto);
        assertEquals("Notebook", producto.getNombreProducto());
    }

    @Test
    public void testGuardarProducto(){
        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Notebook");
        dto.setDescripcionProducto("Notebook 15 pulgadas");
        dto.setPrecioProducto(500000.0);
        dto.setIdCategoria(1);
        dto.setIdMarca(1);

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(createCategoria()));
        when(marcaRepository.findById(1)).thenReturn(Optional.of(createMarca()));
        when(productoRepository.save(any(Producto.class))).thenReturn(createProducto());

        Producto guardado = productoService.guardarProducto(dto);
        assertNotNull(guardado);
        assertEquals("Notebook", guardado.getNombreProducto());
    }

    @Test
    public void testActualizarProducto(){
        Producto existente = createProducto();
        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Notebook Gamer");

        when(productoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(productoRepository.save(existente)).thenReturn(existente);

        Producto actualizado = productoService.actualizarProducto(1, dto);
        assertNotNull(actualizado);
        assertEquals("Notebook Gamer", actualizado.getNombreProducto());
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