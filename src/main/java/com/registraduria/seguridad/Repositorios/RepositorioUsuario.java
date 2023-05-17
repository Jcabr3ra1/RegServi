package com.tutorial.seguridad.Repositorios;
import com.tutorial.seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
//Extends es para la herencia
public interface RepositorioUsuario extends MongoRepository<Usuario,String> {
}