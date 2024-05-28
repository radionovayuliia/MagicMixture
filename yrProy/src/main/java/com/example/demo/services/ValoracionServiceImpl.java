package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Coctel;
import com.example.demo.domain.UserRol;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoracion;
import com.example.demo.repository.ValoracionRepository;

@Service
public class ValoracionServiceImpl implements ValoracionService {
    @Autowired
    ValoracionRepository valoracionRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public Valoracion añadir(Valoracion valoracion) {
        Usuario usuarioCon = usuarioService.obtenerUsuarioConectado();
        valoracion.setUsuario(usuarioCon);
        return valoracionRepository.save(valoracion);
    }

    @Override
    public List<Valoracion> obtenerTodosPorUsuario(Usuario usuario) {
        return valoracionRepository.findByUsuario(usuario);
    }

    @Override
    public List<Valoracion> obtenerTodosPorCoctel(Coctel coctel) {
        return valoracionRepository.findByCoctel(coctel);
    }

    @Override
    public Valoracion obtenerPorId(Long id) {
        return valoracionRepository.findById(id).orElse(null);
    }

    public Valoracion editar(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }

    public void borrar(Long id) {
        Valoracion valoracion = valoracionRepository.findById(id).orElse(null);
        Usuario usuarioCon = usuarioService.obtenerUsuarioConectado();
        System.out.println("valoracion" + valoracion);
        System.out.println("usuarioCon" + usuarioCon);
        if ((valoracion != null && (valoracion.getUsuario().equals(usuarioCon)
                || usuarioCon.getRol().equals(UserRol.ADMIN)))) {
            valoracionRepository.deleteById(id);
        }
    }
}
