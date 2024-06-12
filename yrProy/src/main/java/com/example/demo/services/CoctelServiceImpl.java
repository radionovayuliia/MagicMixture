package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Coctel;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.CoctelRepository;

@Service
public class CoctelServiceImpl implements CoctelService {
    @Autowired
    CoctelRepository coctelRepository;

    @Override
    public int anhoActual() {
        return LocalDate.now().getYear();
    }

    @Override
    public String[] listarCocteles() {
        return new String[] { "Mojito", "Cosmopolitan", "Mimosa" };
    }

    @Override
    public Coctel añadir(Coctel coctel) {
        return coctelRepository.save(coctel);
    }

    @Override
    public List<Coctel> obtenerTodos() {
        return coctelRepository.findAll();
    }

    @Override
    public Page<Coctel> obtenerTodos(Pageable pageable) {
        return coctelRepository.findAll(pageable);
    }

    @Override
    public Coctel obtenerPorId(Long id) throws NotFoundException {
        Optional<Coctel> coctel = coctelRepository.findById(id);
        if (coctel.isPresent()) {
            return coctel.get();
        } else {
            throw new NotFoundException("Coctel no encontrado con id " + id);
        }
    }

    @Override
    public boolean verificarStock(Long id, int cantidad) throws NotFoundException {
        Coctel coctel = obtenerPorId(id);
        return coctel.getStock() >= cantidad;
    }

    @Override
    public Coctel editar(Coctel coctel) {
        return coctelRepository.save(coctel);
    }

    @Override
    public void borrar(Long id) throws NotFoundException {
        Coctel coctel = coctelRepository.findById(id).orElseThrow(() -> new NotFoundException("Coctel no encontrado con id " + id));
        coctelRepository.delete(coctel);
    }

    @Override
    public List<Coctel> obtenerPorCategoria(Long idCategoria) {
        return coctelRepository.findByCategoriaId(idCategoria);
    }

    @Override
    public Page<Coctel> obtenerPorCategoria(Long idCategoria, Pageable pageable) {
        return coctelRepository.findByCategoriaId(idCategoria, pageable);
    }

    @Override
    public List<Coctel> buscarPorNombre(String nombre) {
        return coctelRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Coctel> buscarPorNombreYCategoria(String nombre, String categoria) {
        if (categoria == null || categoria.isEmpty() || categoria.equalsIgnoreCase("Todas")) {
            return coctelRepository.findByNombreContainingIgnoreCase(nombre);
        } else {
            return coctelRepository.findByNombreContainingIgnoreCaseAndCategoriaNombreContainingIgnoreCase(nombre, categoria);
        }
    }

    @Override
    public Page<Coctel> buscarPorNombre(String nombre, Pageable pageable) {
        return coctelRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    @Override
    public Page<Coctel> buscarPorNombreYCategoria(String nombre, Long categoriaId, Pageable pageable) {
        return coctelRepository.findByNombreContainingIgnoreCaseAndCategoriaId(nombre, categoriaId, pageable);
    }

    @Override
    public void reducirStock(Long coctelId, int cantidad) throws NotFoundException {
        Coctel coctel = obtenerPorId(coctelId);
        if (coctel.getStock() >= cantidad) {
            coctel.setStock(coctel.getStock() - cantidad);
            coctelRepository.save(coctel);
        } else {
            throw new IllegalArgumentException("Stock insuficiente para el cóctel " + coctel.getNombre());
        }
    }
    
}
