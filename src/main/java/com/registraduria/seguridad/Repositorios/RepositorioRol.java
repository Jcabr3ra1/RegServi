package com.tutorial.seguridad.Repositorios;
import com.tutorial.seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;
//Extends es para la herencia
public interface RepositorioRol  extends MongoRepository <Rol,String> {
}
