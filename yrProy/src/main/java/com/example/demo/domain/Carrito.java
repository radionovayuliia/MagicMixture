package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private Map<Long, CarritoItem> items = new HashMap<>();

    public Map<Long, CarritoItem> getItems() {
        return items;
    }

    public void addItem(Long coctelId, String nombre, int cantidad, double precio) {
        if (items.containsKey(coctelId)) {
            CarritoItem item = items.get(coctelId);
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            items.put(coctelId, new CarritoItem(coctelId, nombre, cantidad, precio));
        }
    }

    public void removeItem(Long coctelId) {
        items.remove(coctelId);
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.values().stream().mapToDouble(CarritoItem::getTotal).sum();
    }

    public static class CarritoItem {
        private Long id;
        private String nombre;
        private int cantidad;
        private double precio;

        public CarritoItem(Long id, String nombre, int cantidad, double precio) {
            this.id = id;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precio = precio;
        }

        public Long getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public double getPrecio() {
            return precio;
        }

        public double getTotal() {
            return cantidad * precio;
        }
    }
}
