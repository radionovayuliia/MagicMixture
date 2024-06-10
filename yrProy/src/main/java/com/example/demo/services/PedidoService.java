package com.example.demo.services;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Usuario;

import java.util.List;

public interface PedidoService {
    Pedido crearPedido(Usuario usuario, List<LineaPedido> líneasPedido);
    List<Pedido> obtenerPedidosPorUsuario(Usuario usuario);
}

