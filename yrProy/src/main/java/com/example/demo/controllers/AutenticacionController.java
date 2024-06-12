package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.UserRol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/magicmixture")
public class AutenticacionController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Nombre de usuario o contraseña incorrectos.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Has cerrado sesión correctamente.");
        }
        return "main/iniciarSesion";
    }

    @GetMapping("/registro")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "main/registrarse";
    }

    @PostMapping("/registro/submit")
    public String showRegisterSubmit(@Valid @ModelAttribute("usuario") Usuario nuevoUsuario, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "main/registrarse";
        }
        if (usuarioService.obtenerPorNombre(nuevoUsuario.getNombre()) != null) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "main/registrarse";
        }
        nuevoUsuario.setRol(UserRol.USER);
        Usuario user = usuarioService.añadir(nuevoUsuario);
        System.out.println(user);
        model.addAttribute("message", "Usuario registrado correctamente.");
        return "main/registrarse";
    }
    
}

