package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.UserRol;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.ContraseñaNuevaDto;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/magicmixture/")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listaUsuarios")
    public String showUsuario(@RequestParam(required = false) Integer op, Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        if (op != null)
            model.addAttribute("msg", obtenerMensajeError(op));
        return "usuario/usuariosView";
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/nuevoUsuario")
    public String showNew(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/nuevoUsuarioView";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevoUsuario/submit")
    public String showNewSubmit(@Valid @ModelAttribute("usuario") Usuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "usuario/nuevoUsuarioView";
        nuevoUsuario.setRol(UserRol.USER);
        usuarioService.añadir(nuevoUsuario);
        return "redirect:/api";
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editUsuario")
    public String showEditForm(Model model) {
        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("usuario", usuarioConectado);
        model.addAttribute("contraseñaNuevaDto", new ContraseñaNuevaDto());
        return "usuario/editarUsuario";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editUsuario/submit")
    public String showEditSubmit(@Valid @ModelAttribute("usuario") Usuario usuarioForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contraseñaNuevaDto", new ContraseñaNuevaDto());
            return "usuario/editarUsuario";
        }
        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        usuarioConectado.setEmail(usuarioForm.getEmail());
        usuarioService.editar(usuarioConectado);
        return "redirect:/magicmixture/editUsuario";
    }

    @PostMapping("/cambiarContraseña/submit")
    public String showCambioContraseñaSubmit(@Valid @ModelAttribute("contraseñaNuevaDto") ContraseñaNuevaDto contraseñaForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
            model.addAttribute("usuario", usuarioConectado);
            return "usuario/editarUsuario";
        }

        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        if (!passwordEncoder.matches(contraseñaForm.getContraseñaActual(), usuarioConectado.getPassword())) {
            model.addAttribute("error", "La contraseña actual no es correcta.");
            model.addAttribute("usuario", usuarioConectado);
            model.addAttribute("contraseñaNuevaDto", contraseñaForm);
            return "usuario/editarUsuario";
        }

        usuarioConectado.setContraseña(passwordEncoder.encode(contraseñaForm.getNuevaContraseña()));
        usuarioService.editar(usuarioConectado);
        model.addAttribute("message", "Contraseña cambiada exitosamente.");
        model.addAttribute("usuario", usuarioConectado);
        model.addAttribute("contraseñaNuevaDto", new ContraseñaNuevaDto());
        return "usuario/editarUsuario";
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id, Model model) {
        usuarioService.borrar(id);
        return "redirect:/magicmixture/listaUsuarios?op=3";
    }

    public String obtenerMensajeError(Integer op) {
        switch (op) {
            case 1:
                return "Usuario añadido correctamente";
            case 2:
                return "Usuario editado correctamente";
            case 3:
                return "Usuario borrado correctamente";
            case 4:
                return "Usuario no se ha podido editar correctamente";
            case 5:
                return "Usuario no se ha podido borrar correctamente";
            default:
                return "Datos incorrectos";
        }
    }
}

