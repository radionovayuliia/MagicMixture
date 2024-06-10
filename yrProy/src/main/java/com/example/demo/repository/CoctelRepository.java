package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Coctel;

public interface CoctelRepository extends JpaRepository<Coctel, Long> {
    List<Coctel> findByCategoriaId(Long idCategoria);

    List<Coctel> findByNombreContainingIgnoreCase(String nombre);

    List<Coctel> findByNombreContainingIgnoreCaseAndCategoriaNombreContainingIgnoreCase(String nombre, String categoriaNombre);

    Page<Coctel> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Page<Coctel> findByNombreContainingIgnoreCaseAndCategoriaId(String nombre, Long categoriaId, Pageable pageable);

    Page<Coctel> findByCategoriaId(Long idCategoria, Pageable pageable);
}
