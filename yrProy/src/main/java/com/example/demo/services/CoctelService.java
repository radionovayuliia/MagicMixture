package com.example.demo.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Coctel> buscarPorNombre(String nombre);

    List<Coctel> buscarPorNombreYCategoria(String nombre, String categoria);

    Page<Coctel> obtenerTodos(Pageable pageable);

    Page<Coctel> obtenerPorCategoria(Long idCategoria, Pageable pageable);

    Page<Coctel> buscarPorNombre(String nombre, Pageable pageable);

    Page<Coctel> buscarPorNombreYCategoria(String nombre, Long categoriaId, Pageable pageable);
}
