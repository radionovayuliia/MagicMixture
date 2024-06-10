package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;
import com.example.demo.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public Pedido crearPedido(Usuario usuario, List<LineaPedido> líneasPedido) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setLíneasPedido(líneasPedido);
        pedido.setFecha(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioId(usuario.getId());
    }
}
