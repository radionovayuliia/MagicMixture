package com.example.demo.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.domain.Coctel;
import com.example.demo.exceptions.NotFoundException;

public interface CoctelService {
    int anhoActual();

    String[] listarCocteles();

    Coctel añadir(Coctel coctel);

    List<Coctel> obtenerTodos();

    Coctel obtenerPorId(Long id) throws NotFoundException;

    boolean verificarStock(Long id, int cantidad) throws NotFoundException;

    Coctel editar(Coctel coctel);

    void borrar(Long id) throws NotFoundException;

    List<Coctel> obtenerPorCategoria(Long idCategoria);

    List<Coctel> buscarPorNombre(String nombre);

    List<Coctel> buscarPorNombreYCategoria(String nombre, String categoria);

    Page<Coctel> obtenerTodos(Pageable pageable);

    Page<Coctel> obtenerPorCategoria(Long idCategoria, Pageable pageable);

    Page<Coctel> buscarPorNombre(String nombre, Pageable pageable);

    Page<Coctel> buscarPorNombreYCategoria(String nombre, Long categoriaId, Pageable pageable);

    void reducirStock(Long id, int cantidad) throws NotFoundException;
}
