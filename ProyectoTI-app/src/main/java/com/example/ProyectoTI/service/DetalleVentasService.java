package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.DetalleVentaDTO;
import com.example.ProyectoTI.model.DetalleVenta;
import com.example.ProyectoTI.model.Producto;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.repository.DetalleVentaRepository;
import com.example.ProyectoTI.repository.ProductoRepository;
import com.example.ProyectoTI.repository.VentaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class DetalleVentasService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<DetalleVenta> obtenerTodos() {
        log.info("Obteniendo todos los detalles de venta");
        return detalleVentaRepository.findAll();
    }

    public Venta procesarVenta(Integer idVenta, List<DetalleVenta> detalles){
        log.info("Procesando venta con id: {}", idVenta);
        Venta venta = ventaRepository.findById(idVenta).orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + idVenta));
        double total = 0.0;
        for (DetalleVenta detalle : detalles){
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + detalle.getProducto().getIdProducto()));
            if (producto.getStockProducto() <= 0) {
                throw new RuntimeException("El producto: " + producto.getNombreProducto() + " no tiene stock disponible");
            }

            if(detalle.getCantidadProducto() > producto.getStockProducto()){
                throw new RuntimeException("La cantidad solicitada para el producto: " + producto.getNombreProducto() + " excede el stock disponible");
            }
            producto.setStockProducto(producto.getStockProducto() - detalle.getCantidadProducto());
            productoRepository.save(producto);
            detalle.setPrecioUnitario(producto.getPrecioProducto());
            detalle.setVenta(venta);
            total += producto.getPrecioProducto() * detalle.getCantidadProducto();
            detalleVentaRepository.save(detalle);
        }
        venta.setPrecioFinal(total);
        return ventaRepository.save(venta);
    }

    public DetalleVenta obtenerPorId(Integer idDetalleVenta) {
        log.info("Obteniendo detalle de venta con id: {}", idDetalleVenta);
        return detalleVentaRepository.findById(idDetalleVenta).orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con id: " + idDetalleVenta));
    }

    public List<DetalleVenta> obtenerPorDetalleVenta(Integer idVenta) {
        log.info("Obteniendo detalles de venta para venta con id: {}", idVenta);
        return detalleVentaRepository.findByVenta_IdVenta(idVenta);
    }

    public DetalleVenta guardarDetalle(DetalleVentaDTO dto) {
        log.info("Guardando detalle de venta para venta con id: {} y producto con id: {}", dto.getIdVenta(), dto.getIdProducto());
        Venta venta = ventaRepository.findById(dto.getIdVenta()).orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + dto.getIdVenta()));
        Producto producto = productoRepository.findById(dto.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + dto.getIdProducto()));
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidadProducto(dto.getCantidadProducto());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());

        return detalleVentaRepository.save(detalle);
    }

    public DetalleVenta actualizarDetalle(Integer idDetalleVenta, DetalleVentaDTO dto) {
        log.info("Actualizando detalle de venta con id: {}", idDetalleVenta);
        DetalleVenta detalle = detalleVentaRepository.findById(idDetalleVenta).orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con id: " + idDetalleVenta));
        Venta venta = ventaRepository.findById(dto.getIdVenta()).orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + dto.getIdVenta()));
        Producto producto = productoRepository.findById(dto.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + dto.getIdProducto()));
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidadProducto(dto.getCantidadProducto());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        return detalleVentaRepository.save(detalle);
    }

    public String eliminarDetalle(Integer idDetalleVenta) {
        log.info("Eliminando detalle de venta con id: {}", idDetalleVenta);
        try {
            if (!detalleVentaRepository.existsById(idDetalleVenta)) {
                return "Detalle de venta no encontrado con id: " + idDetalleVenta;
            }
            detalleVentaRepository.deleteById(idDetalleVenta);
            return "Detalle de venta eliminado correctamente";
        } catch (Exception e) {
            return "Error al eliminar el detalle de venta: " + e.getMessage();
        }
    }
}
