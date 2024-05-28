package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Usuario;

public interface UsuarioService {
    
    Usuario añadir(Usuario usuario);

    List<Usuario> obtenerTodos();

    Usuario obtenerPorId(Long id);

    Usuario editar(Usuario usuario);

    void borrar(Long id);

    Usuario obtenerUsuarioConectado();
}
