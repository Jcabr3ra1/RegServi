package com.tutorial.seguridad.Controladores;

import com.tutorial.seguridad.Modelos.Usuario;
import com.tutorial.seguridad.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")

public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    //Endpoint para obtener una lista con todos los Usuarios
    @GetMapping("")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public  Usuario create(@RequestBody Usuario infoUsuario){
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);
    }
    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual= this.miRepositorioUsuario.findById(id).orElse(null);
        return usuarioActual;
    }
    //Actualizacion
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody Usuario infoUsuario){
        Usuario usuarioActual= this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual != null){
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return  this.miRepositorioUsuario.save(usuarioActual);
        }
        else{
            return null;
        }
    }
    //Eliminar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public  void delete(@PathVariable String id){
        Usuario usuarioActual = this.miRepositorioUsuario.findById(id).orElse(null);
        if(usuarioActual !=null){
            this.miRepositorioUsuario.delete(usuarioActual);
        }
    }
    //Encriptar la constraseña
    public String convertirSHA256(String password){
        MessageDigest md= null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        //stringbuffer ayuda a cargar en memoria caracters o cadenas
        StringBuffer sb = new StringBuffer();
        for (byte b:hash){
            //Format para darle formato a la cadena encriptada
            //append para agregar elementos a la cadena
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

}
