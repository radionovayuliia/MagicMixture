package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Coctel;

public interface CoctelService {
    int anhoActual();

    String[] listarCocteles();

    Coctel añadir(Coctel coctel);

    List<Coctel> obtenerTodos();

    Coctel obtenerPorId(Long id);

    Coctel editar(Coctel coctel);

    void borrar(Long id);

    List<Coctel> obtenerPorCategoria(Long idCategoria);
}
