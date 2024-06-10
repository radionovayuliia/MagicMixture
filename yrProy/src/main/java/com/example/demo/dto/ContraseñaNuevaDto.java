package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;

public class ContraseñaNuevaDto {
    @NotEmpty
    private String contraseñaActual;
    
    @NotEmpty
    private String nuevaContraseña;

    public String getContraseñaActual() {
        return contraseñaActual;
    }

    public void setContraseñaActual(String contraseñaActual) {
        this.contraseñaActual = contraseñaActual;
    }

    public String getNuevaContraseña() {
        return nuevaContraseña;
    }

    public void setNuevaContraseña(String nuevaContraseña) {
        this.nuevaContraseña = nuevaContraseña;
    }
}
