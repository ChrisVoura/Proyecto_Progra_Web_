
package com.Mercado.controller;

import com.Mercado.entity.Consultas;
import com.Mercado.entity.Orden;
import com.Mercado.entity.Producto;
import com.Mercado.entity.Usuario;
import com.Mercado.service.IConsultaService;
import com.Mercado.service.IOrdenService;
import com.Mercado.service.IUsuarioService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService usuarioservice;
    @Autowired
    private IOrdenService ordenservice;
    @Autowired
    private IConsultaService consultaservice;
    
    @GetMapping("/registro")
    public String crear(){
      return"usuario/registro";  
    }
    @PostMapping("/save")
    public String save(Usuario usuario,@RequestParam("img")MultipartFile imagen){
            if(!imagen.isEmpty()){
            Path directorioImg= Paths.get("src//main//resources//static/images");
            String rutaAbsoluta=directorioImg.toFile().getAbsolutePath();
            try {
                byte[] bytesImg=imagen.getBytes();
                Path rutaCompleta= Paths.get( rutaAbsoluta+"//"+imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytesImg);
                
                usuario.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {
               e.printStackTrace();
            }
    }
         usuario.setTipo("USER");
         usuarioservice.save(usuario);
         return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
       
        return "usuario/login";
    }
    @PostMapping("/access")
    public String access(Usuario usuario, HttpSession session){
         Optional<Usuario> u= usuarioservice.findByEmail(usuario.getEmail());
         if(u.isPresent()){
         session.setAttribute("idusuario", u.get().getId());
         if(u.get().getTipo().equals("ADMIN")){
             return "redirect:/administrador";  
         }else{
             return"redirect:/";
         }
         }else{
             logger.info("Usuario no existe");
         }
        return "redirect:/";
    }
    @GetMapping("/compras")
    public String getbuy(HttpSession session, Model model){
        model.addAttribute("sesion", session.getAttribute("idusuario"));
       Usuario usuario=usuarioservice.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        List<Orden> ordenes= ordenservice.findByUsuario(usuario);
        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }
    @GetMapping("/cerrar")
    public String closesession(HttpSession session){
        session.removeAttribute("idusuario");
        return"redirect:/";
    }
    @GetMapping("/Perfil")
    public String perfil( Model model,HttpSession session){
          Usuario usuario=usuarioservice.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        model.addAttribute("usuarios", usuario);
        return "usuario/Perfil";
    }
    
    @GetMapping("/consulta")
    public String crear(Model model){
         model.addAttribute("consulta", new Consultas());      
        return "usuario/Consultas";
    }
    @PostMapping("/saveConsulta")
     public String saveConsultas( Consultas consultas,HttpSession session){
       Usuario u= usuarioservice.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        consultas.setUsuario(u);
      consultaservice.save(consultas);
      return "redirect:/";
   }
}
