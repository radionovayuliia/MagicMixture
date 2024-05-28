package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Coctel;
import com.example.demo.repository.CoctelRepository;

@Service
public class CoctelServiceImpl implements CoctelService {
    @Autowired
    CoctelRepository coctelRepository;

    public int anhoActual() {
        return LocalDate.now().getYear();
    }

    public String[] listarCocteles() {
        return new String[] { "Mojito", "Cosmopolitan", "Mimosa" };
    }

    public Coctel añadir(Coctel coctel) {
        return coctelRepository.save(coctel);
    }

    public List<Coctel> obtenerTodos() {
        return coctelRepository.findAll();
    }

    public Coctel obtenerPorId(Long id) {
        return coctelRepository.findById(id).orElse(null);
    }

    public Coctel editar(Coctel coctel) {
        return coctelRepository.save(coctel);
    }

    public void borrar(Long id) {
        Coctel coctel = obtenerPorId(id);
        coctelRepository.delete(coctel);
    }

    public List<Coctel> obtenerPorCategoria(Long idCategoria) {
        return coctelRepository.findByCategoriaId(idCategoria);
    }

}
