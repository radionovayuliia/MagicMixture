package com.example.demo;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Coctel;
import com.example.demo.domain.TipoCoctel;
import com.example.demo.domain.UserRol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.CoctelService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.ValoracionService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(CoctelService coctelService, CategoriaService categoriaService,
			UsuarioService usuarioService, ValoracionService valoracionService, PasswordEncoder passwordEncoder) {
		return args -> {
			Categoria cat1 = categoriaService.añadir(
					new Categoria(0L, "Categoria A"));
			Categoria cat2 = categoriaService.añadir(
					new Categoria(0L, "Categoria B"));
			coctelService.añadir(
					new Coctel(0L, "Mojito", TipoCoctel.Aperetivo, "ron, menta, lima, azúcar, agua con gas", "Rapida",
							cat1));
			coctelService.añadir(
					new Coctel(0L, "Caipirinha", TipoCoctel.Aperetivo, "piña", "Rapida", cat1));
			coctelService.añadir(
					new Coctel(0L, "Bloody Mary", TipoCoctel.Aperetivo, "zumo de tomate", "Rapida", cat2));
			usuarioService
					.añadir(new Usuario(0L, "Ana", "ana@gmail.com", "12345", UserRol.USER,
							LocalDate.now()));
			usuarioService.añadir(new Usuario(0L, "Pepe", "pepe@gmail.com", "1111", UserRol.MANAGER,
					LocalDate.now()));
			usuarioService.añadir(new Usuario(0L, "Yuliia", "yuliia@gmail.com", "qwerty",
					UserRol.ADMIN, LocalDate.now()));
		};
	}

}
