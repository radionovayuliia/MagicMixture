package com.example.demo.controllers;

import java.time.YearMonth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.FormInfoContacta;

@Controller
@RequestMapping("/magicmixture")
public class MainController {
    
    @GetMapping({""})
    public String showIndex(@RequestParam(required = false) String usuario, Model model) {
        int fecha = YearMonth.now().getYear();
        model.addAttribute("user", usuario);
        model.addAttribute("anho", fecha);
        return "main/index";
    }

    @GetMapping("/quienes-somos")
    public String showQuienSomos() {
        return "main/aboutUsView";
    }

    @GetMapping("/contacto")
    public String showConacta(Model model) {
        model.addAttribute("formInfoContacta", new FormInfoContacta());
        return "main/contactView";
    }

    @PostMapping("/contacto/submit")
    public String showContactaConNosotros(FormInfoContacta formInfoContacta, Model model) {
        model.addAttribute("nombre", formInfoContacta.getNombre());
        model.addAttribute("correo", formInfoContacta.getCorreo());
        model.addAttribute("opcion", formInfoContacta.getOpcion());
        model.addAttribute("comentario", formInfoContacta.getComentario());
        model.addAttribute("condiciones", formInfoContacta.getCondiciones());
        return "main/datosProcesados";
    }
}
