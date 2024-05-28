package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.UserRol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.AutoregistroService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/autoregistro")
public class AutoregistroController {

    @Autowired
    AutoregistroService autoregistroService;

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "autoregistro/nuevoUsuarioView";
    }

    @PostMapping("/nuevo/submit")
    public String registrarNuevoUsuario(@Valid @ModelAttribute("usuario") Usuario nuevoUsuario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "autoregistro/nuevoUsuarioView";
        nuevoUsuario.setRol(UserRol.USER);
        autoregistroService.guardarUsuarioNoConfirmado(nuevoUsuario);
        return "redirect:/api";
    }
}
