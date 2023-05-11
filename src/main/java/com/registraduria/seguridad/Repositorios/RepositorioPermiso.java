package com.tutorial.seguridad.Repositorios;
import com.tutorial.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

//Extends es para la herencia
public interface RepositorioPermiso extends  MongoRepository <Permiso,String>{
}
