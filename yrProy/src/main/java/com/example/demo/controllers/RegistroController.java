// package com.example.demo.controllers;

// import com.example.demo.domain.Usuario;
// import com.example.demo.services.UsuarioService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// @RequestMapping("/magicmixture")
// public class RegistroController {

//     @Autowired
//     private UsuarioService usuarioService;

//     @GetMapping("/login")
//     public String showLoginForm() {
//         return "auth/login";
//     }

//     @GetMapping("/register")
//     public String showRegisterForm(Model model) {
//         model.addAttribute("usuario", new Usuario());
//         return "auth/register";
//     }

//     @PostMapping("/register")
//     public String registerUser(Usuario usuario) {
//         usuarioService.registrarUsuario(usuario);
//         return "redirect:/magicmixture/login";
//     }
// }
