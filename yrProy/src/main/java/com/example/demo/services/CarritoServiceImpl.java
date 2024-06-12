package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.domain.Carrito;
import com.example.demo.exceptions.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@SessionScope
public class CarritoServiceImpl implements CarritoService {

    private static final Logger logger = LoggerFactory.getLogger(CarritoServiceImpl.class);

    @Autowired
    private CoctelService coctelService;

    private final Carrito carrito = new Carrito();

    @Override
    public Carrito obtenerCarrito() {
        return carrito;
    }

    @Override
    public void añadirCoctel(Long coctelId, String nombre, int cantidad, double precio) {
        carrito.addItem(coctelId, nombre, cantidad, precio);
        logger.info("Añadido cóctel: {}, cantidad: {}, precio: {}", nombre, cantidad, precio);
    }

    @Override
    public void borrarCoctel(Long coctelId) {
        carrito.removeItem(coctelId);
        logger.info("Eliminado cóctel con ID: {}", coctelId);
    }

    @Override
    public void vaciarCarrito() {
        carrito.clear();
        logger.info("Carrito vaciado");
    }

    @Override
    public void procesarCompra() throws NotFoundException {
        logger.info("Procesando compra");
        for (Carrito.CarritoItem item : carrito.getItems().values()) {
            coctelService.reducirStock(item.getId(), item.getCantidad());
            logger.info("Stock reducido para el cóctel con ID: {}, cantidad: {}", item.getId(), item.getCantidad());
        }
        vaciarCarrito();
        logger.info("Compra procesada y carrito vaciado");
    }
}
