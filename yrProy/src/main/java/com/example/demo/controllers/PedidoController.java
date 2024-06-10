package com.example.demo.controllers;

import com.example.demo.domain.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CoctelService coctelService;

    @PostMapping("/crearPedido")
    public String crearPedido() {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        Carrito carrito = carritoService.getCarrito();
        List<LineaPedido> líneasPedido = carrito.getCoctelesEnCarrito().entrySet().stream()
                .map(entry -> {
                    LineaPedido línea = new LineaPedido();
                    línea.setCoctel(coctelService.obtenerPorId(entry.getKey()));
                    línea.setCantidad(entry.getValue());
                    return línea;
                })
                .collect(Collectors.toList());

        pedidoService.crearPedido(usuario, líneasPedido);
        carritoService.clear();
        return "redirect:/pedidos";
    }

    @GetMapping
    public String verPedidos(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuario);
        model.addAttribute("pedidos", pedidos);
        return "pedidos/listaPedidosView";
    }
}

