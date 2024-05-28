package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Coctel;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoracion;

public interface ValoracionService {

    Valoracion añadir(Valoracion Valoracion);

    List<Valoracion> obtenerTodosPorUsuario(Usuario usuario);

    List<Valoracion> obtenerTodosPorCoctel(Coctel coctel);

    Valoracion obtenerPorId(Long id);

    Valoracion editar(Valoracion usuario);

    void borrar(Long id);
}
