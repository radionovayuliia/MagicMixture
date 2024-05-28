package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Categoria;
import com.example.demo.exceptions.NotFoundException;

public interface CategoriaService {
    Categoria añadir(Categoria categoria);

    List<Categoria> obtenerTodos();

    Categoria obtenerPorId(Long id) throws NotFoundException;

    Categoria editar(Categoria categoria);

    void borrar(Long id) throws NotFoundException;
}
