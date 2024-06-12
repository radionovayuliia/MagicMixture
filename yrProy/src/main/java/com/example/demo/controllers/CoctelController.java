package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("")
    public String showProductos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) Long categoriaId,
                                Model model) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Coctel> cocteles;
        if (categoriaId != null && categoriaId > 0) {
            cocteles = coctelService.obtenerPorCategoria(categoriaId, pageable);
            try {
                model.addAttribute("categoriaSeleccionada", categoriaService.obtenerPorId(categoriaId));
            } catch (NotFoundException e) {
                model.addAttribute("error", e.getMessage());
            }
        } else {
            cocteles = coctelService.obtenerTodos(pageable);
            model.addAttribute("categoriaSeleccionada", new Categoria(0L, "Todas"));
        }
        model.addAttribute("listaCocteles", cocteles.getContent());
        model.addAttribute("totalPages", cocteles.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
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
        try {
            Coctel coctel = coctelService.obtenerPorId(id);
            model.addAttribute("bebida", coctel);
            model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
            return "coctel/editarCoctelView";
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
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
        try {
            coctelService.borrar(id);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
        return "redirect:/cocteles?op=3";
    }

    @GetMapping("/delete/{cat}")
    public String showDelete(@PathVariable long cat) throws NotFoundException {
        if (coctelService.obtenerPorCategoria(cat).size() == 0)
            categoriaService.borrar(cat);
        return "redirect:/cocteles";
    }

    @GetMapping("/porCateg/{idCat}")
    public String showListInCategory(@PathVariable Long idCat, @RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Coctel> cocteles = coctelService.obtenerPorCategoria(idCat, pageable);
        model.addAttribute("listaCocteles", cocteles.getContent());
        model.addAttribute("totalPages", cocteles.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        try {
            model.addAttribute("categoriaSeleccionada", categoriaService.obtenerPorId(idCat));
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "coctel/detalleCoctel";
    }

    @GetMapping("/buscar")
    public String buscarCocteles(@RequestParam("nombre") String nombre, 
                                 @RequestParam(value = "categoria", required = false) Long categoriaId,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Coctel> resultados;
        if (categoriaId == null || categoriaId == 0) {
            resultados = coctelService.buscarPorNombre(nombre, pageable);
            model.addAttribute("categoriaSeleccionada", new Categoria(0L, "Todas"));
        } else {
            resultados = coctelService.buscarPorNombreYCategoria(nombre, categoriaId, pageable);
            try {
                model.addAttribute("categoriaSeleccionada", categoriaService.obtenerPorId(categoriaId));
            } catch (NotFoundException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        model.addAttribute("listaCocteles", resultados.getContent());
        model.addAttribute("totalPages", resultados.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        return "coctel/detalleCoctel";
    }

    @GetMapping("/{id}")
    public String showCoctelDetail(@PathVariable Long id, Model model) {
        try {
            Coctel coctel = coctelService.obtenerPorId(id);
            List<Coctel> similares = coctelService.obtenerPorCategoria(coctel.getCategoria().getId());
            similares.removeIf(c -> c.getId().equals(id));
            model.addAttribute("coctel", coctel);
            model.addAttribute("similares", similares);
            return "coctel/infoCoctel";
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "errorView";
        }
    }
    

        @PostMapping("/update-stock/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> payload) throws NotFoundException{
        int cantidad = payload.get("cantidad");
        Coctel coctel = coctelService.obtenerPorId(id);
        if (coctel != null && coctel.getStock() >= cantidad) {
            coctel.setStock(coctel.getStock() - cantidad);
            coctelService.añadir(coctel);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock insuficiente");
        }
    }
}
