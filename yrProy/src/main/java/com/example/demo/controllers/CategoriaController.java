package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Categoria;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.CategoriaService;

import jakarta.validation.Valid;

@Controller
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categorias")
    public String showCategorias(@RequestParam(required = false) Integer op, Model model) {
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        if (op != null) {
            switch (op) {
                case 1: model.addAttribute("msg", "Categoria añadida correctamente"); break;
                case 2: model.addAttribute("msg", "Categoria editada correctamente"); break;
                case 3: model.addAttribute("msg", "Categoria borrada correctamente"); break;
                case 4: model.addAttribute("msg", "Categoria no se ha podido editar correctamente"); break;
                case 5: model.addAttribute("msg", "Categoria no se ha podido borrar correctamente"); break;
                case 6: model.addAttribute("msg", "Datos incorrectos"); break;
            }
        }
        return "/categoria/categoriasView";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/newCategory")
    public String showNew(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "/categoria/nuevaCategoriaView";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevaCategoria/submit")
    public String showNewSubmit(@Valid Categoria categoria, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) 
            return "redirect:/categorias?op=6";
        categoriaService.añadir(categoria);    
        return "redirect:/categorias?op=1";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Categoria categoria;
        try {
            categoria = categoriaService.obtenerPorId(id);
        } catch (NotFoundException e) {
            return "redirect:/categorias?op=4";
        }
        model.addAttribute("categoria", categoria);
        return "/categoria/editarCategoria";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editCategoria/submit")
    public String showEditSubmit(@Valid Categoria categoria, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/categorias?op=6";
        categoriaService.editar(categoria);
        return "redirect:/categorias?op=2";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id, Model model) {
        try {
            categoriaService.borrar(id);
        } catch (NotFoundException e) {
            return "redirect:/categorias?op=5";
        }
        return "redirect:/categorias?op=3";
    }
}

