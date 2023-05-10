package com.registraduria.seguridad.Controladores;

import com.registraduria.seguridad.Modelos.Usuario;
import com.registraduria.seguridad.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    //Enpointpara obtener una lista con todos los usuarios
    @GetMapping("")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario){
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual = this.miRepositorioUsuario.findById(id).orElse(null);
        return usuarioActual;
    }

    //Actualizar
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody Usuario infoUsuario){
        Usuario usuariaActual = this.miRepositorioUsuario.findById(id).orElse(null);
        if (usuariaActual != null){
            usuariaActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuariaActual.setCorreo(infoUsuario.getCorreo());
            usuariaActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return this.miRepositorioUsuario.save(usuariaActual);
        }else {
            return null;
        }
    }
    //eliminar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActUsuario = this.miRepositorioUsuario.findById(id).orElse(null);
        if (usuarioActUsuario != null){
            this.miRepositorioUsuario.delete(usuarioActUsuario);
        }
    }
    //Encriptar la contraseña

    public String convertirSHA256(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b:hash){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }
}
