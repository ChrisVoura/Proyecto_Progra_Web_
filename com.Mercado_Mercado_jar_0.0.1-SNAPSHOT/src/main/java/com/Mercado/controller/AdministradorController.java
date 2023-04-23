
package com.Mercado.controller;

import com.Mercado.entity.Producto;
import com.Mercado.service.IConsultaService;
import com.Mercado.service.IProductoService;
import com.Mercado.service.IUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    private IProductoService productoservice;
    @Autowired
    private IUsuarioService usuarioservice;
    @Autowired
    private IConsultaService consultaservice;
    @GetMapping("")
    public String home(Model model){
        List<Producto> productos= productoservice.findAll();
        model.addAttribute("productos",productos);
        return "administrador/home";
    }
    @GetMapping("/usuarios")
    public String getuser(Model model){
        model.addAttribute("usuarios", usuarioservice.findAll());
        return "administrador/usuarios";
    }
    @GetMapping("/usuariosC")
    public String getConsulta(Model model){
        model.addAttribute("consultas", consultaservice.findAll());
        return "administrador/usuariosC";
    }
}
