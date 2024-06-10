package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private Map<Long, Integer> coctelesEnCarrito = new HashMap<>();

    public Map<Long, Integer> getCoctelesEnCarrito() {
        return coctelesEnCarrito;
    }

    public void addCoctel(Long coctelId, int cantidad) {
        coctelesEnCarrito.put(coctelId, coctelesEnCarrito.getOrDefault(coctelId, 0) + cantidad);
    }

    public void removeCoctel(Long coctelId) {
        coctelesEnCarrito.remove(coctelId);
    }

    public void clear() {
        coctelesEnCarrito.clear();
    }
}