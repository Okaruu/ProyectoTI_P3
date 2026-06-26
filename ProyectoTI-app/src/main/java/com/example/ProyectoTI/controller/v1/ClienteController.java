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

import com.example.ProyectoTI.DTO.ClienteDTO;
import com.example.ProyectoTI.model.Cliente;
import com.example.ProyectoTI.service.ClienteService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes.")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosClientes(){
        List<Cliente> clientes = clienteService.obtenerTodos();
        if(!clientes.isEmpty()){
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontraron clientes", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Integer idCliente){
        try{
            Cliente cliente = clienteService.obtenerPorId(idCliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error al obtener el cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarCliente(@RequestBody Cliente cliente){
        try{
            Cliente clienteGuardado = clienteService.guardarCliente(cliente);
            return new ResponseEntity<>(clienteGuardado, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error al guardar el cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{idCliente}")
    public ResponseEntity<?> editarCliente(@PathVariable Integer idCliente, @RequestBody ClienteDTO clienteDTO){
        try{
            Cliente clienteEditado = clienteService.actualizarCliente(idCliente, clienteDTO);
            return new ResponseEntity<>(clienteEditado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error al actualizar el cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idCliente}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Integer idCliente, @RequestBody ClienteDTO clienteDTO){
        try{
            Cliente clienteActualizado = clienteService.actualizarCliente(idCliente, clienteDTO);
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error al actualizar el cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Integer idCliente){
        try{
            clienteService.eliminarCliente(idCliente);
            return new ResponseEntity<>("Cliente eliminado exitosamente", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error al eliminar el cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}