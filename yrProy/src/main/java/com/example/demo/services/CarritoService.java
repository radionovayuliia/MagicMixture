package com.example.demo.services;

import com.example.demo.domain.Carrito;
import com.example.demo.exceptions.NotFoundException;

public interface CarritoService {  
    Carrito obtenerCarrito();

    void añadirCoctel(Long coctelId, String nombre, int cantidad, double precio);

    void borrarCoctel(Long coctelId);

    void vaciarCarrito();

    void procesarCompra() throws NotFoundException;
}
