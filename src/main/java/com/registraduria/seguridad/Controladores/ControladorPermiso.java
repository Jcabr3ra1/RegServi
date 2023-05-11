package com.tutorial.seguridad.Controladores;

import com.tutorial.seguridad.Modelos.Permiso;
import com.tutorial.seguridad.Repositorios.RepositorioPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/permisos")

public class ControladorPermiso {
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    //Endpoint para obtener una lista con todos los Permiso
    @GetMapping("")
    public List<Permiso> index(){
        return this.miRepositorioPermiso.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public  Permiso create(@RequestBody Permiso infoPermiso){
        return this.miRepositorioPermiso.save(infoPermiso);
    }
    @GetMapping("{id}")
    public Permiso show(@PathVariable String id){
        Permiso permisoActual= this.miRepositorioPermiso.findById(id).orElse(null);
        return permisoActual;
    }
    //Actualizacion
    @PutMapping("{id}")
    public Permiso update(@PathVariable String id,@RequestBody Permiso infoPermiso){
        Permiso permisoActual= this.miRepositorioPermiso.findById(id).orElse(null);
        if(permisoActual != null){
            permisoActual.setUrl(infoPermiso.getUrl());
            permisoActual.setMetodo(infoPermiso.getMetodo());
            return  this.miRepositorioPermiso.save(permisoActual);
        }
        else{
            return null;
        }
    }
    //Eliminar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public  void delete(@PathVariable String id){
        Permiso permisoActual = this.miRepositorioPermiso.findById(id).orElse(null);
        if(permisoActual !=null){
            this.miRepositorioPermiso.delete(permisoActual);
        }
    }
}
