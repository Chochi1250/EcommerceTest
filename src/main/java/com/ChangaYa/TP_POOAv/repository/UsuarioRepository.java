package com.ChangaYa.TP_POOAv.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ChangaYa.TP_POOAv.model.Usuario;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario,ObjectId> {
    Usuario findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    Optional<Usuario>   findByRole(String role);
    Optional<Usuario> findBynombre(String email); //Para uso con Dtos
    
}
