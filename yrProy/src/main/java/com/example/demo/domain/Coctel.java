package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Coctel {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String nombre;
    private TipoCoctel tipoCoctel;
    private String ingredientes;
    private Float precio;
    private Integer stock;

    @ToString.Exclude
    @ManyToOne
    private Categoria categoria;

}
