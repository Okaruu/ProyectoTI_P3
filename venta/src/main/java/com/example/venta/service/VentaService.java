package com.example.venta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.venta.DTO.DetalleVentaDTO;
import com.example.venta.DTO.VentaDTO;
import com.example.venta.model.DetalleVenta;
import com.example.venta.model.Venta;
import com.example.venta.repository.VentaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> obtenerTodos() {
        log.info("Obteniendo todas las ventas");
        return ventaRepository.findAll();
    }

    public Venta obtenerPorId(Integer idVenta) {
        log.info("Obteniendo venta con id: {}", idVenta);
        return ventaRepository.findById(idVenta).orElse(null);
    }

    public Venta guardarVenta(VentaDTO ventaDTO) {
        log.info("Guardando venta del cliente: {}", ventaDTO.getNombreCliente());
        Venta venta = convertirAEntidad(ventaDTO);
        if (venta.getPrecioFinal() == null) {
            venta.setPrecioFinal(calcularTotal(venta.getDetalles()));
        }
        return ventaRepository.save(venta);
    }

    public Venta actualizarVenta(Integer idVenta, VentaDTO ventaDTO) {
        log.info("Actualizando venta con id: {}", idVenta);
        Venta venta = ventaRepository.findById(idVenta).orElseThrow(() -> new RuntimeException("No se encontro la venta con id: " + idVenta));
        if (ventaDTO.getRutCliente() != null) venta.setRutCliente(ventaDTO.getRutCliente());
        if (ventaDTO.getNombreCliente() != null) venta.setNombreCliente(ventaDTO.getNombreCliente());
        if (ventaDTO.getTelefonoCliente() != null) venta.setTelefonoCliente(ventaDTO.getTelefonoCliente());
        if (ventaDTO.getEmailCliente() != null) venta.setEmailCliente(ventaDTO.getEmailCliente());
        if (ventaDTO.getRutEmpleado() != null) venta.setRutEmpleado(ventaDTO.getRutEmpleado());
        if (ventaDTO.getNombreEmpleado() != null) venta.setNombreEmpleado(ventaDTO.getNombreEmpleado());
        if (ventaDTO.getFechaVenta() != null) venta.setFechaVenta(ventaDTO.getFechaVenta());
        if (ventaDTO.getDetalles() != null) {
            if (venta.getDetalles() == null) {
                venta.setDetalles(new ArrayList<>());
            } else {
                venta.getDetalles().clear();
            }
            venta.getDetalles().addAll(convertirDetalles(ventaDTO.getDetalles(), venta));
            venta.setPrecioFinal(calcularTotal(venta.getDetalles()));
        } else if (ventaDTO.getPrecioFinal() != null) {
            venta.setPrecioFinal(ventaDTO.getPrecioFinal());
        }
        return ventaRepository.save(venta);
    }

    public String eliminarVenta(Integer idVenta) {
        log.info("Eliminando venta con id: {}", idVenta);
        try {
            Venta venta = ventaRepository.findById(idVenta)
                    .orElseThrow(() -> new RuntimeException("No se encontro la venta con id: " + idVenta));
            ventaRepository.delete(venta);
            return "Venta eliminada correctamente";
        } catch (Exception e) {
            return "Error al eliminar la venta: " + e.getMessage();
        }
    }

    private Double calcularTotal(List<DetalleVenta> detalles) {
        if (detalles == null) {
            return 0.0;
        }
        return detalles.stream()
                .mapToDouble(d -> d.getCantidadProducto() * d.getPrecioUnitario())
                .sum();
    }

    private List<DetalleVenta> convertirDetalles(List<DetalleVentaDTO> detallesDTO, Venta venta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        for (DetalleVentaDTO d : detallesDTO) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setNombreProducto(d.getNombreProducto());
            detalle.setCantidadProducto(d.getCantidadProducto());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setVenta(venta);
            detalles.add(detalle);
        }
        return detalles;
    }

    private Venta convertirAEntidad(VentaDTO dto) {
        Venta venta = new Venta();
        venta.setRutCliente(dto.getRutCliente());
        venta.setNombreCliente(dto.getNombreCliente());
        venta.setTelefonoCliente(dto.getTelefonoCliente());
        venta.setEmailCliente(dto.getEmailCliente());
        venta.setRutEmpleado(dto.getRutEmpleado());
        venta.setNombreEmpleado(dto.getNombreEmpleado());
        venta.setFechaVenta(dto.getFechaVenta());
        venta.setPrecioFinal(dto.getPrecioFinal());
        venta.setDetalles(dto.getDetalles() != null ? convertirDetalles(dto.getDetalles(), venta) : new ArrayList<>());
        return venta;
    }

}