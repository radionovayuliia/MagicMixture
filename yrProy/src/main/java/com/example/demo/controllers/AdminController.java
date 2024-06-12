package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Coctel;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.CoctelService;

@Controller
public class AdminController {
    @Autowired
    CoctelService coctelService;

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/administracion")
    public String administracion(Model model) {
        List<Coctel> listaCocteles = coctelService.obtenerTodos();
        List<Categoria> listaCategorias = categoriaService.obtenerTodos();
        model.addAttribute("listaCocteles", listaCocteles);
        model.addAttribute("listaCategorias", listaCategorias);
        return "administracion";
    }
}
