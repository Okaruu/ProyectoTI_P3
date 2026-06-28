package com.example.ProyectoTI.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.ProductoDTO;
import com.example.ProyectoTI.assemblers.ProductoModelAssembler;
import com.example.ProyectoTI.model.Producto;
import com.example.ProyectoTI.service.ProductoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("productoControllerV2")
@RequestMapping("/api/v2/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos.")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> obtenerTodosLosInstrumentos() {
        List<EntityModel<ProductoDTO>> productos = productoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).obtenerTodosLosInstrumentos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> obtenerProductoPorId(@PathVariable Integer idProducto) {
        try {
            ProductoDTO producto = productoService.obtenerPorId(idProducto);
            return ResponseEntity.ok(assembler.toModel(producto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/categoria/{idCategoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> listarPorCategoria(@PathVariable Integer idCategoria) {
        List<EntityModel<ProductoDTO>> productos = productoService.obtenerProductosPorCategoria(idCategoria).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).listarPorCategoria(idCategoria)).withSelfRel()
        ));
    }

    @GetMapping(value = "/marca/{idMarca}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> listarPorMarca(@PathVariable Integer idMarca) {
        List<EntityModel<ProductoDTO>> productos = productoService.obtenerProductosPorMarca(idMarca).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                productos,
                linkTo(methodOn(ProductoControllerV2.class).listarPorMarca(idMarca)).withSelfRel()
        ));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> agregarProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            Producto guardado = productoService.guardarProducto(productoDTO);
            ProductoDTO dto = productoService.obtenerPorId(guardado.getIdProducto());
            return ResponseEntity
                    .created(linkTo(methodOn(ProductoControllerV2.class).obtenerProductoPorId(dto.getIdProducto())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> editarProducto(@PathVariable Integer idProducto, @RequestBody ProductoDTO producto) {
        try {
            Producto editado = productoService.actualizarProducto(idProducto, producto);
            ProductoDTO dto = productoService.obtenerPorId(editado.getIdProducto());
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> actualizarProducto(@PathVariable Integer idProducto, @RequestBody ProductoDTO productoDTO) {
        try {
            Producto actualizado = productoService.actualizarProducto(idProducto, productoDTO);
            ProductoDTO dto = productoService.obtenerPorId(actualizado.getIdProducto());
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer idProducto) {
        String resultado = productoService.eliminarProducto(idProducto);
        if (resultado.contains("eliminado correctamente")) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}