package com.punto_de_venta.service_inventario.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_inventario.model.MovimientoInventario;
import com.punto_de_venta.service_inventario.model.Stock;
import com.punto_de_venta.service_inventario.repository.MovimientoInventarioRepository;
import com.punto_de_venta.service_inventario.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class InventarioService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Transactional
    public MovimientoInventario nuevoMovimiento(Long idProd, MovimientoInventario mov) {
        Boolean existeEnStock = webClientBuilder.build()
                .get().uri("http://localhost:4425/api/v1/productos/existe/" + idProd)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (existeEnStock == null || !existeEnStock) {
            throw new RuntimeException("Operación detenida: el producto " + idProd + "No se encuentra en el catálogo");
        }

        Stock stockActual = stockRepository.findById(idProd).orElseGet(
                () -> {
                    Stock nuevoStock = new Stock();
                    nuevoStock.setIdProducto(idProd);
                    nuevoStock.setCantidad(BigDecimal.valueOf(0));
                    nuevoStock.setStockMinimo(BigDecimal.valueOf(5));
                    return stockRepository.save(nuevoStock);
                });

        Double cantidadActual = stockActual.getCantidad().doubleValue();
        Double cantidadMovimiento = mov.getMonto().doubleValue();
        if (mov.getTipoMovimiento().equalsIgnoreCase("ENTRADA")) {
            stockActual.setCantidad(BigDecimal.valueOf(cantidadActual + cantidadMovimiento));
        } else if (mov.getTipoMovimiento().equalsIgnoreCase("SALIDA")) {
            if (cantidadActual<cantidadMovimiento) {
                throw new RuntimeException("Error: stock insuficiente para realizar salida");
            }
            stockActual.setCantidad(BigDecimal.valueOf(cantidadActual-cantidadMovimiento));
        }

        stockRepository.save(stockActual);
        mov.setStock(stockActual);
        mov.setFechaMovimiento(LocalDateTime.now());
        return movimientoInventarioRepository.save(mov);
    }

    

    // --- MÉTODOS ESPECÍFICOS PARA EL MOVIMIENTO DE INVENTARIO ---
    public Optional<MovimientoInventario> obtenerMovimientoPorId(Long id){
        MovimientoInventario mov = movimientoInventarioRepository.findById(id).orElse(null);
        if (mov!=null) {
            MovimientoInventario movi = movimientoInventarioRepository.findById(id).orElse(null);
            movi.setStock(stockConProducto(movi.getStock()));
            return Optional.of(movi);
        } else {
            throw new RuntimeException("El movimiento no existe");
        }
    }

    public Optional<List<MovimientoInventario>> obtenerMovimientosPorDia(LocalDate dia){
        

        LocalDateTime inicio = dia.atStartOfDay();
        LocalDateTime fin = dia.atStartOfDay().plusHours(24);

        return movimientoInventarioRepository.findByFechaMovimientoBetween(inicio, fin);
    }

    public Optional<List<MovimientoInventario>> obtenerMovimientosDesde(LocalDate fecha){
        LocalDateTime desde = fecha.atStartOfDay();
        return movimientoInventarioRepository.findByFechaMovimientoAfter(desde);
    }

    public Optional<List<MovimientoInventario>> obtenerMovimientosEntre(LocalDate in, LocalDate fin){
        LocalDateTime inicio = in.atStartOfDay();
        LocalDateTime findia = fin.atTime(23,59,59,59);
        return movimientoInventarioRepository.findByFechaMovimientoBetween(inicio, findia);
    }

    public List<MovimientoInventario> listarMovimientos(){
        List<MovimientoInventario> listaMovimientos = movimientoInventarioRepository.findAll();
        for (int i = 0; i < listaMovimientos.size(); i++) {
            listaMovimientos.get(i).setStock(stockConProducto(listaMovimientos.get(i).getStock()));
        }
        return listaMovimientos;
    }

    // ---MÉTODOS ESPECIFICOS PARA STOCK---
    public Stock obtenerStockId(Long id){
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock!= null) {
            return stockConProducto(stock);
        } else {
            throw new RuntimeException("El stock no existe");
        }
    }

    private Stock stockConProducto(Stock stock){
        if (stock.getIdProducto()!=null) {
            try{
                Object producto = webClientBuilder.build()
                .get().uri("http://localhost:4425/api/v1/productos/"+stock.getIdProducto())
                .retrieve().bodyToMono(Object.class).block();

                stock.setDatosProducto(producto);
            } catch (Exception e){
                stock.setDatosProducto("Información de producto no disponible");
                e.printStackTrace(); 
                stock.setDatosProducto("Error: " + e.getMessage());
            }
        }
        return stock;
    } 
    
   public List<Stock> alertaBajoStock(){
        List<Stock> productosBajoStock = stockRepository.findByBajoStockMinimo();
        productosBajoStock.forEach(stock -> {this.stockConProducto(stock);});
        return productosBajoStock;
   }

   public void actualizarStockMinimo(Long id, BigDecimal nuevoMinimo){
        Long filasAfectadas = stockRepository.updateStockMinimo(id, nuevoMinimo);
        if (filasAfectadas == 0) {
            throw new RuntimeException("No se encontró la ID especificada. No se actualizó nada.");
        }
   }
   public List<Stock> listarStock() {
    List<Stock> stocks = stockRepository.findAll();
    stocks.forEach(this::stockConProducto); 
    return stocks;
}

}
