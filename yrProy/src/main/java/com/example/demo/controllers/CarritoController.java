// package com.example.demo.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.example.demo.domain.Carrito;
// import com.example.demo.exceptions.NotFoundException;
// import com.example.demo.services.CarritoService;
// import com.example.demo.services.CoctelService;

// @Controller
// @RequestMapping("/magicmixture/carrito")
// public class CarritoController {

//     @Autowired
//     private CarritoService carritoService;

//     @Autowired
//     private CoctelService coctelService;

//     @GetMapping
//     public String verCarrito(Model model) {
//         model.addAttribute("carrito", carritoService.obtenerCarrito());
//         return "carrito/carritoView";
//     }

//     @PostMapping("/agregar")
//     public String agregarAlCarrito(@RequestParam("coctelId") Long coctelId,
//             @RequestParam("cantidad") int cantidad,
//             RedirectAttributes redirectAttributes) {
//         try {
//             var coctel = coctelService.obtenerPorId(coctelId);
//             Carrito carrito = carritoService.obtenerCarrito();
//             Carrito.CarritoItem item = carrito.getItems().get(coctelId);
//             int cantidadExistente = (item != null) ? item.getCantidad() : 0;
//             int nuevaCantidadTotal = cantidadExistente + cantidad;

//             if (coctelService.verificarStock(coctelId, nuevaCantidadTotal)) {
//                 carritoService.añadirCoctel(coctelId, coctel.getNombre(), cantidad, coctel.getPrecio());
//                 redirectAttributes.addFlashAttribute("msg", "Añadido al carrito!");
//             } else {
//                 redirectAttributes.addFlashAttribute("error",
//                         "Stock insuficiente para el cóctel " + coctel.getNombre());
//             }
//             return "redirect:/cocteles/" + coctelId;
//         } catch (NotFoundException e) {
//             redirectAttributes.addFlashAttribute("error", "Cóctel no encontrado");
//             return "redirect:/cocteles";
//         }
//     }

//     @PostMapping("/agregar2")
//     public String agregarAlCarrito2(@RequestParam("coctelId") Long coctelId,
//             @RequestParam("cantidad") int cantidad,
//             RedirectAttributes redirectAttributes) {
//         try {
//             var coctel = coctelService.obtenerPorId(coctelId);
//             Carrito carrito = carritoService.obtenerCarrito();
//             Carrito.CarritoItem item = carrito.getItems().get(coctelId);
//             int cantidadExistente = (item != null) ? item.getCantidad() : 0;
//             int nuevaCantidadTotal = cantidadExistente + cantidad;

//             if (coctelService.verificarStock(coctelId, nuevaCantidadTotal)) {
//                 carritoService.añadirCoctel(coctelId, coctel.getNombre(), cantidad, coctel.getPrecio());
//                 redirectAttributes.addFlashAttribute("msg", "Añadido al carrito!");
//             } else {
//                 redirectAttributes.addFlashAttribute("error",
//                         "Stock insuficiente para el cóctel " + coctel.getNombre());
//             }
//             return "redirect:/cocteles";
//         } catch (NotFoundException e) {
//             redirectAttributes.addFlashAttribute("error", "Cóctel no encontrado");
//             return "redirect:/cocteles";
//         }
//     }

//     @PostMapping("/remover")
//     public String removerDelCarrito(@RequestParam("coctelId") Long coctelId, RedirectAttributes redirectAttributes) {
//         carritoService.borrarCoctel(coctelId);
//         redirectAttributes.addFlashAttribute("msg", "Eliminado del carrito!");
//         return "redirect:/magicmixture/carrito";
//     }

//     @PostMapping("/vaciar")
//     public String vaciarCarrito(RedirectAttributes redirectAttributes) {
//         carritoService.vaciarCarrito();
//         redirectAttributes.addFlashAttribute("msg", "Carrito vaciado!");
//         return "redirect:/magicmixture/carrito";
//     }

//     @PostMapping("/incrementar")
//     public String incrementarCantidad(@RequestParam("coctelId") Long coctelId, RedirectAttributes redirectAttributes) {
//         try {
//             var coctel = coctelService.obtenerPorId(coctelId);
//             Carrito carrito = carritoService.obtenerCarrito();
//             Carrito.CarritoItem item = carrito.getItems().get(coctelId);
//             int cantidadExistente = (item != null) ? item.getCantidad() : 0;
//             int nuevaCantidadTotal = cantidadExistente + 1;

//             if (coctelService.verificarStock(coctelId, nuevaCantidadTotal)) {
//                 carritoService.añadirCoctel(coctelId, coctel.getNombre(), 1, coctel.getPrecio());
//                 redirectAttributes.addFlashAttribute("msg", "Cantidad incrementada!");
//             } else {
//                 redirectAttributes.addFlashAttribute("error",
//                         "Stock insuficiente para el cóctel " + coctel.getNombre());
//             }
//             return "redirect:/magicmixture/carrito";
//         } catch (NotFoundException e) {
//             redirectAttributes.addFlashAttribute("error", "Cóctel no encontrado");
//             return "redirect:/magicmixture/carrito";
//         }
//     }

//     @PostMapping("/decrementar")
//     public String decrementarCantidad(@RequestParam("coctelId") Long coctelId, RedirectAttributes redirectAttributes) {
//         Carrito carrito = carritoService.obtenerCarrito();
//         Carrito.CarritoItem item = carrito.getItems().get(coctelId);
//         if (item != null) {
//             int cantidadExistente = item.getCantidad();
//             if (cantidadExistente > 1) {
//                 carritoService.añadirCoctel(coctelId, item.getNombre(), -1, item.getPrecio());
//                 redirectAttributes.addFlashAttribute("msg", "Cantidad decrementada!");
//             } else {
//                 carritoService.borrarCoctel(coctelId);
//                 redirectAttributes.addFlashAttribute("msg", "Producto eliminado del carrito!");
//             }
//         }
//         return "redirect:/magicmixture/carrito";
//     }

//     @GetMapping("/comprar")
//     public String comprar(RedirectAttributes redirectAttributes) {
//         try {
//             carritoService.procesarCompra();
//             redirectAttributes.addFlashAttribute("msg", "Compra realizada con éxito!");
//             return "redirect:/magicmixture/carrito";
//         } catch (Exception e) {
//             redirectAttributes.addFlashAttribute("error", "Error al procesar la compra: " + e.getMessage());
//             return "redirect:/magicmixture/carrito";
//         }
//     }

// }

package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Carrito;
import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Usuario;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.CarritoService;
import com.example.demo.services.CoctelService;
import com.example.demo.services.PedidoService;
import com.example.demo.services.UsuarioService;

@Controller
@RequestMapping("/magicmixture/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CoctelService coctelService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carritoService.obtenerCarrito());
        return "carrito/carritoView";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam("coctelId") Long coctelId,
            @RequestParam("cantidad") int cantidad,
            RedirectAttributes redirectAttributes) {
        try {
            var coctel = coctelService.obtenerPorId(coctelId);
            Carrito carrito = carritoService.obtenerCarrito();
            Carrito.CarritoItem item = carrito.getItems().get(coctelId);
            int cantidadExistente = (item != null) ? item.getCantidad() : 0;
            int nuevaCantidadTotal = cantidadExistente + cantidad;

            if (coctelService.verificarStock(coctelId, nuevaCantidadTotal)) {
                carritoService.añadirCoctel(coctelId, coctel.getNombre(), cantidad, coctel.getPrecio());
                redirectAttributes.addFlashAttribute("msg", "Añadido al carrito!");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Stock insuficiente para el cóctel " + coctel.getNombre());
            }
            return "redirect:/cocteles/" + coctelId;
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Cóctel no encontrado");
            return "redirect:/cocteles";
        }
    }

    @PostMapping("/agregar2")
    public String agregarAlCarrito2(@RequestParam("coctelId") Long coctelId,
            @RequestParam("cantidad") int cantidad,
            RedirectAttributes redirectAttributes) {
        try {
            var coctel = coctelService.obtenerPorId(coctelId);
            Carrito carrito = carritoService.obtenerCarrito();
            Carrito.CarritoItem item = carrito.getItems().get(coctelId);
            int cantidadExistente = (item != null) ? item.getCantidad() : 0;
            int nuevaCantidadTotal = cantidadExistente + cantidad;

            if (coctelService.verificarStock(coctelId, nuevaCantidadTotal)) {
                carritoService.añadirCoctel(coctelId, coctel.getNombre(), cantidad, coctel.getPrecio());
                redirectAttributes.addFlashAttribute("msg", "Añadido al carrito!");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Stock insuficiente para el cóctel " + coctel.getNombre());
            }
            return "redirect:/cocteles";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Cóctel no encontrado");
            return "redirect:/cocteles";
        }
    }

    @PostMapping("/remover")
    public String removerDelCarrito(@RequestParam("coctelId") Long coctelId, RedirectAttributes redirectAttributes) {
        carritoService.borrarCoctel(coctelId);
        redirectAttributes.addFlashAttribute("msg", "Eliminado del carrito!");
        return "redirect:/magicmixture/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(RedirectAttributes redirectAttributes) {
        carritoService.vaciarCarrito();
        redirectAttributes.addFlashAttribute("msg", "Carrito vaciado!");
        return "redirect:/magicmixture/carrito";
    }

    @PostMapping("/incrementar")
    public String incrementarCantidad(@RequestParam("coctelId") Long coctelId, RedirectAttributes redirectAttributes) {
        try {
            var coctel = coctelService.obtenerPorId(coctelId);
            Carrito carrito = carritoService.obtenerCarrito();
            Carrito.CarritoItem item = carrito.getItems().get(coctelId);
            int cantidadExistente = (item != null) ? item.getCantidad() : 0;
            int nuevaCantidadTotal = cantidadExistente + 1;

            if (coctelService.verificarStock(coctelId, nuevaCantidadTotal)) {
                carritoService.añadirCoctel(coctelId, coctel.getNombre(), 1, coctel.getPrecio());
                redirectAttributes.addFlashAttribute("msg", "Cantidad incrementada!");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Stock insuficiente para el cóctel " + coctel.getNombre());
            }
            return "redirect:/magicmixture/carrito";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Cóctel no encontrado");
            return "redirect:/magicmixture/carrito";
        }
    }

    @PostMapping("/decrementar")
    public String decrementarCantidad(@RequestParam("coctelId") Long coctelId, RedirectAttributes redirectAttributes) {
        Carrito carrito = carritoService.obtenerCarrito();
        Carrito.CarritoItem item = carrito.getItems().get(coctelId);
        if (item != null) {
            int cantidadExistente = item.getCantidad();
            if (cantidadExistente > 1) {
                carritoService.añadirCoctel(coctelId, item.getNombre(), -1, item.getPrecio());
                redirectAttributes.addFlashAttribute("msg", "Cantidad decrementada!");
            } else {
                carritoService.borrarCoctel(coctelId);
                redirectAttributes.addFlashAttribute("msg", "Producto eliminado del carrito!");
            }
        }
        return "redirect:/magicmixture/carrito";
    }

    @GetMapping("/comprar")
    public String comprar(RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioConectado();
            Carrito carrito = carritoService.obtenerCarrito();
            List<LineaPedido> lineasPedido = carrito.getItems().entrySet().stream()
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
            redirectAttributes.addFlashAttribute("msg", "Compra realizada con éxito!");
            return "redirect:/magicmixture/mis-pedidos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la compra: " + e.getMessage());
            return "redirect:/magicmixture/carrito";
        }
    }

}
