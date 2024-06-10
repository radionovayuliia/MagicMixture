package com.example.demo.services;

import com.example.demo.domain.Carrito;

public interface CarritoService {  
    Carrito getCarrito();

    void addCoctel(Long coctelId, int cantidad);

    void removeCoctel(Long coctelId);

    void clear();
}
