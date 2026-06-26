package com.example.ProyectoTI.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.ProductoDTO;
import com.example.ProyectoTI.model.Producto;
import com.example.ProyectoTI.service.ProductoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name= "Productos", description = "Operaciones relacionadas con los productos.")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodosLosInstrumentos(){
        List<ProductoDTO> productos = productoService.obtenerTodos();
        if(productos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
    @GetMapping({"/{idProducto}"})
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Integer idProducto){
        try{
            ProductoDTO producto = productoService.obtenerPorId(idProducto);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoDTO>> listarPorCategoria(@PathVariable Integer idCategoria){
        List<ProductoDTO> productos = productoService.obtenerProductosPorCategoria(idCategoria);
        if(productos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);  
    }

    @PostMapping
    public ResponseEntity<Producto> agregarProducto(@RequestBody ProductoDTO productoDTO){
        try{
            Producto productoGuardado = productoService.guardarProducto(productoDTO);
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{idProducto}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Integer idProducto, @RequestBody ProductoDTO producto){
        try{
            Producto productoEditado = productoService.actualizarProducto(idProducto, producto);
            return new ResponseEntity<>(productoEditado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer idProducto, @RequestBody ProductoDTO productoDTO) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(idProducto, productoDTO);
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<List<ProductoDTO>> listarPorMarca(@PathVariable Integer idMarca){
        List<ProductoDTO> productos = productoService.obtenerProductosPorMarca(idMarca);
        if(productos.isEmpty()){
            return new ResponseEntity<>(productos, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(productos, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer idProducto){
        String resultado = productoService.eliminarProducto(idProducto);
        if(resultado.contains("eliminado correctamente")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}

