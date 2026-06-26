package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.ProductoDTO;
import com.example.ProyectoTI.model.Categoria;
import com.example.ProyectoTI.model.Marca;
import com.example.ProyectoTI.model.Producto;
import com.example.ProyectoTI.repository.CategoriaRepository;
import com.example.ProyectoTI.repository.MarcaRepository;
import com.example.ProyectoTI.repository.ProductoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    public List<ProductoDTO> obtenerTodos(){
        log.info("Obteniendo todos los productos");
        return productoRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public ProductoDTO obtenerPorId(Integer idProducto){
        log.info("Obteniendo producto con id: {}", idProducto);
        Producto producto = productoRepository.findById(idProducto).orElseThrow(() -> new RuntimeException("No se encontró el producto con id: " + idProducto));
        return convertirADTO(producto);
    }

    public Producto actualizarProducto(Integer idProducto, ProductoDTO producto) {
        log.info("Actualizando producto con id: {}", idProducto);
        Producto prod = productoRepository.findById(idProducto).orElseThrow(() -> new RuntimeException("No se encontró el producto con id: " + idProducto));
        if (producto.getNombreProducto() != null) {
            prod.setNombreProducto(producto.getNombreProducto());
        }
        if (producto.getPrecioProducto() != null) {
            prod.setPrecioProducto(producto.getPrecioProducto());
        }
        if (producto.getDescripcionProducto() != null) {
            prod.setDescripcionProducto(producto.getDescripcionProducto());
        }
        return productoRepository.save(prod);
    }

    public List<ProductoDTO> obtenerProductosPorCategoria(Integer idCategoria){
        log.info("Obteniendo productos de la categoría con id: {}", idCategoria);
        return productoRepository.buscarPorCategoria(idCategoria).stream().map(this::convertirADTO).toList();
    }

    public List<ProductoDTO> obtenerProductosPorMarca(Integer idMarca){
        log.info("Obteniendo productos de la marca con id: {}", idMarca);
        return productoRepository.buscarPorMarca(idMarca).stream().map(this::convertirADTO).toList();
    }

    public Producto guardarProducto(ProductoDTO dto) {
        log.info("Guardando producto: {}", dto.getNombreProducto());
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Marca marca = marcaRepository.findById(dto.getIdMarca()).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcionProducto(dto.getDescripcionProducto());
        producto.setPrecioProducto(dto.getPrecioProducto());
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        return productoRepository.save(producto);
    }

    private ProductoDTO convertirADTO(Producto producto){
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setIdProducto(producto.getIdProducto());
        productoDTO.setNombreProducto(producto.getNombreProducto());
        productoDTO.setIdCategoria(producto.getCategoria().getIdCategoria());
        productoDTO.setIdMarca(producto.getMarca().getIdMarca());
        productoDTO.setDescripcionProducto(producto.getDescripcionProducto());
        productoDTO.setPrecioProducto(producto.getPrecioProducto());
        return productoDTO;
    }

    public String eliminarProducto(Integer idProducto){
        log.info("Eliminando producto con id: {}", idProducto);
        try{
            if(!productoRepository.existsById(idProducto)){
                return "No se encontró el producto con id: " + idProducto;
            }
            productoRepository.deleteById(idProducto);
            return "Producto eliminado correctamente";
        }catch(Exception e){
            return "Error al eliminar el producto: " + e.getMessage();
        }
    }

}