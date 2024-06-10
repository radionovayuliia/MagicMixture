package com.example.demo.services;

import com.example.demo.domain.Carrito;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CarritoServiceImpl implements CarritoService {

    final Carrito carrito = new Carrito();

    @Override
    public Carrito getCarrito() {
        return carrito;
    }

    @Override
    public void addCoctel(Long coctelId, int cantidad) {
        carrito.addCoctel(coctelId, cantidad);
    }

    @Override
    public void removeCoctel(Long coctelId) {
        carrito.removeCoctel(coctelId);
    }

    @Override
    public void clear() {
        carrito.clear();
    }
}
