package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Coctel;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.CoctelRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    CoctelRepository coctelRepository;

    @Override
    public Categoria añadir(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> obtenerTodos() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria obtenerPorId(Long id) throws NotFoundException {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
    }

    @Override
    public Categoria editar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void borrar(Long id) throws NotFoundException {
        Categoria categoria = obtenerPorId(id); 
        List<Coctel> cocteles = coctelRepository.findByCategoriaId(id); 
    
        if (cocteles.isEmpty()) {
            categoriaRepository.delete(categoria); 
        } else {
            throw new NotFoundException("No se puede borrar la categoría porque tiene cocteles asociados");
        }
    }
    
}
