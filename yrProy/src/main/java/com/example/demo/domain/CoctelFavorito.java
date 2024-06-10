package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CoctelFavorito {
    private String nombre;
    private String coctel;
    private String receta;
    private String opcion;
    private Boolean acepto;
    private String ficheroSubido;
}
