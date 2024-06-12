package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Valoracion;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.CoctelService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.ValoracionService;

import jakarta.validation.Valid;

@RequestMapping("/valoracion")
@Controller
public class ValoracionController {
    @Autowired
    public CoctelService coctelService;

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public ValoracionService valoracionService;

    @GetMapping("/producto/{id}")
    public String showListProductos(@PathVariable Long id, Model model)  throws NotFoundException{
        model.addAttribute("listaValoracion",
                valoracionService.obtenerTodosPorCoctel(coctelService.obtenerPorId(id)));
        return "valoracion/valoracionListView";
    }

    @GetMapping("/usuario/{id}")
    public String showListUsuarios(@PathVariable Long id, Model model) {
        model.addAttribute("listaValoracion",
                valoracionService.obtenerTodosPorUsuario(usuarioService.obtenerPorId(id)));
        return "valoracion/valoracionListView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        model.addAttribute("listaCocteles", coctelService.obtenerTodos());
        model.addAttribute("valoracionesForm", new Valoracion());
        return "valoracion/valoracionNewView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid @ModelAttribute("valoracionesForm") Valoracion nuevaValoracion, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "valoracion/valoracionNewView";
        valoracionService.añadir(nuevaValoracion);
        return "redirect:/cocteles";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id, Model model) {
        valoracionService.borrar(id);
        return "redirect:/cocletes";
    }
}
