package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.CarritoService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carritoService.getCarrito());
        return "carrito/carritoView";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam("coctelId") Long coctelId,
                                   @RequestParam("cantidad") int cantidad) {
        carritoService.addCoctel(coctelId, cantidad);
        return "redirect:/carrito";
    }

    @PostMapping("/remover")
    public String removerDelCarrito(@RequestParam("coctelId") Long coctelId) {
        carritoService.removeCoctel(coctelId);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito() {
        carritoService.clear();
        return "redirect:/carrito";
    }
}

