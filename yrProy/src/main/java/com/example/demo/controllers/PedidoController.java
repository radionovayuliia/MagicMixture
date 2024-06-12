package com.example.demo.controllers;

import com.example.demo.domain.*;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/magicmixture/mis-pedidos")
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
    public String crearPedido(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        Carrito carrito = carritoService.obtenerCarrito();
        List<LineaPedido> lineasPedido;
        try {
            lineasPedido = carrito.getItems().entrySet().stream()
                    .map(entry -> {
                        LineaPedido linea = new LineaPedido();
                        try {
                            linea.setCoctel(coctelService.obtenerPorId(entry.getKey()));
                        } catch (NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        linea.setCantidad(entry.getValue().getCantidad());
                        return linea;
                    })
                    .collect(Collectors.toList());

            pedidoService.crearPedido(usuario, lineasPedido);
            carritoService.vaciarCarrito();
            return "redirect:/pedidos";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al crear el pedido: " + e.getCause().getMessage());
            return "error/404"; 
        }
    }

    @GetMapping
    public String verPedidos(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuario);
        model.addAttribute("pedidos", pedidos);
        return "pedidos/listaPedidosView";
    }
}
