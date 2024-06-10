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
					new Categoria(0L, "Primera Cita"));
			Categoria cat2 = categoriaService.añadir(
					new Categoria(0L, "Risas y Amigos"));
			Categoria cat3 = categoriaService.añadir(
					new Categoria(0L, "Suspiro Vintage"));
			Categoria cat4 = categoriaService.añadir(
					new Categoria(0L, "Viajero Mundial"));
			Categoria cat5 = categoriaService.añadir(
					new Categoria(0L, "Misterio Profundo"));
			Categoria cat6 = categoriaService.añadir(
					new Categoria(0L, "Pa' Olvidar Todo"));
			coctelService.añadir(
					new Coctel(0L, "Mojito", TipoCoctel.Aperetivo, "ron, menta, lima, azúcar, agua con gas", 25.00f, 4,
							cat1));
			coctelService.añadir(
					new Coctel(0L, "Caipirinha", TipoCoctel.Aperetivo, "piña", 30.09f, 4, cat1));
			coctelService.añadir(
					new Coctel(0L, "Bloody Mary", TipoCoctel.Aperetivo, "zumo de tomate", 30.00f, 8, cat2));
			coctelService.añadir(
					new Coctel(0L, "Margarita", TipoCoctel.Aperetivo, "tequila, triple sec, jugo de lima", 25.00f, 8,
							cat3));

			coctelService.añadir(
					new Coctel(0L, "Piña Colada", TipoCoctel.Aperetivo, "ron, crema de coco, jugo de piña", 35.00f, 10,
							cat4));

			coctelService.añadir(
					new Coctel(0L, "Mai Tai", TipoCoctel.Aperetivo, "ron, curaçao de naranja, jugo de lima, jarabe de orgeat", 20.00f, 7, cat5));

			coctelService.añadir(
					new Coctel(0L, "Tequila Sunrise", TipoCoctel.LongDrink, "tequila, jugo de naranja, granadina", 28.00f, 9, cat6));

			coctelService.añadir(
					new Coctel(0L, "Cosmopolitan", TipoCoctel.Aperetivo, "vodka, triple sec, jugo de arándano, jugo de lima", 22.00f, 8, cat1));
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
