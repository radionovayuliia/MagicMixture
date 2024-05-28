package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Coctel;
import com.example.demo.domain.CoctelFavorito;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.CoctelService;

import jakarta.validation.Valid;

@RequestMapping("/cocteles")
@Controller
public class CoctelController {
    @Autowired
    CoctelService coctelService;
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/miCoctelFavorito")
    public String showFormularioFavorito(Model model) {
        model.addAttribute("coctelFavorito", new CoctelFavorito());
        return "/favCoctel/formCoctel";
    }

    @PostMapping("/miCoctelFavorito/submit")
    public String showCoctelFavorito(CoctelFavorito coctelFavorito, Model model,
            @RequestParam("miFichero") MultipartFile myfile) {
        model.addAttribute("nombre", coctelFavorito.getNombre());
        model.addAttribute("edad", coctelFavorito.getEdad());
        model.addAttribute("coctel", coctelFavorito.getCoctel());
        model.addAttribute("receta", coctelFavorito.getReceta());
        model.addAttribute("opcion", coctelFavorito.getOpcion());
        model.addAttribute("acepto", coctelFavorito.getAcepto());
        return "/favCoctel/datosProcesados";
    }

    @GetMapping("/detalle")
    public String showDetalleCoctel() {
        return "/coctel/detalleCoctel";
    }

    // @GetMapping("/lista")
    // public String showListaCocteles(Model model) {
    // String[] cocteles = coctelService.listarCocteles();
    // model.addAttribute("listaCocteles", cocteles);
    // return "/favCoctel/listaCocteles";
    // }

    @GetMapping("")
    public String showProductos(@RequestParam(required = false) Integer op, Model model) {
        model.addAttribute("listaCocteles", coctelService.obtenerTodos());
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", new Categoria(0L, "Todas"));
        if (op != null) {
            switch (op) {
                case 1:
                    model.addAttribute("msg", "Coctel añadido correctamente");
                    break;
                case 2:
                    model.addAttribute("msg", "Coctel editado correctamente");
                    break;
                case 3:
                    model.addAttribute("msg", "Coctel borrado correctamente");
                    break;
                case 4:
                    model.addAttribute("msg", "Coctel no se ha podido editar correctamente");
                    break;
                case 5:
                    model.addAttribute("msg", "Coctel no se ha podido borrado correctamente");
                    break;
                case 6:
                    model.addAttribute("msg", "Datos incorrectos");
                    break;
            }
        }
        return "/coctel/detalleCoctel";
    }

    @GetMapping("/nuevo")
    public String showNew(Model model) {
        model.addAttribute("coctel", new Coctel());
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        return "coctel/nuevoCoctelView";
    }

    @PostMapping("/nuevo/submit")
    public String showNewSubmit(@Valid Coctel coctel, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/cocteles?op=6";
        coctelService.añadir(coctel);
        return "redirect:/cocteles?op=1";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Coctel coctel;
        coctel = coctelService.obtenerPorId(id);
        model.addAttribute("bebida", coctel);
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        return "coctel/editarCoctelView";
    }

    @PostMapping("/editar/submit")
    public String showEditSubmit(@Valid Coctel coctel, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/cocteles?op=6";
        coctelService.editar(coctel);
        return "redirect:/cocteles?op=2";
    }

    @GetMapping("/borrar/{id}")
    public String showDelete(@PathVariable long id, Model model) {
        coctelService.borrar(id);
        return "redirect:/cocteles?op=3";
    }

    @GetMapping("/delete/{cat}")
    public String showDelete(@PathVariable long cat) throws NotFoundException {
        if (coctelService.obtenerPorCategoria(cat).size() == 0)
            categoriaService.borrar(cat);
        return "redirect:/cocteles";
    }

    @GetMapping("/porCateg/{idCat}")
    public String showListInCategory(@PathVariable Long idCat, Model model) throws NotFoundException {
        model.addAttribute("listaCocteles", coctelService.obtenerPorCategoria(idCat));
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", categoriaService.obtenerPorId(idCat));
        return "coctel/detalleCoctel";
    }
}
