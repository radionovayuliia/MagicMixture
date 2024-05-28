package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String showUsuario(@RequestParam(required = false) Integer op, Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        if (op != null)
            model.addAttribute("msg", obtenerMensajeError(op));
        return "/usuario/usuariosView";
    }

    @GetMapping("/newUser")
    public String showNew(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/nuevoUsuarioView";
    }

    @PostMapping("/nuevoUsuario/submit")
    public String showNewSubmit(@Valid @ModelAttribute("usuario") Usuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "usuario/nuevoUsuarioView";
        nuevoUsuario.setRol(UserRol.USER);
        usuarioService.añadir(nuevoUsuario);
        return "redirect:/api";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/usuario/editarUsuario";
    }

    @PostMapping("/editUsuario/submit")
    public String showEditSubmit(Usuario usuarioForm, BindingResult bindingResult) {
        Usuario usuarioCon = usuarioService.obtenerUsuarioConectado();
        if (bindingResult.hasErrors())
            return "redirect:/?op=6";
        usuarioCon.setEmail(usuarioForm.getEmail());
        usuarioService.editar(usuarioCon);
        return "redirect:/api";
    }

    @GetMapping("/cambiarContraseña")
    public String showCambioContraseña(Model model) {
        model.addAttribute("usuario", new ContraseñaNuevaDto());
        return "/usuario/cambiarContraseña";
    }

    @PostMapping("/cambiarContraseña/submit")
    public String showCambioContraseñaSubmit(ContraseñaNuevaDto contraseñaForm, BindingResult bindingResult) {
        Usuario usuarioContectado = usuarioService.obtenerUsuarioConectado();
        if (bindingResult.hasErrors())
            return "redirect:/?op=6";
        usuarioContectado.setContraseña(contraseñaForm.getContraseña());
        usuarioService.editar(usuarioContectado);
        return "redirect:/api";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id, Model model) {
        usuarioService.borrar(id);
        return "redirect:/?op=3";
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
