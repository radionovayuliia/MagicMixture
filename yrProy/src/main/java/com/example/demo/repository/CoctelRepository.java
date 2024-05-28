package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Coctel;

public interface CoctelRepository extends JpaRepository<Coctel, Long> {
    List<Coctel> findByCategoriaId(Long idCategoria);
}

