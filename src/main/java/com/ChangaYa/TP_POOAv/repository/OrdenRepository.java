package com.ChangaYa.TP_POOAv.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.ChangaYa.TP_POOAv.model.Orden;



import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends MongoRepository<Orden,ObjectId> {
    List<Orden> findByUsuarioCliente(String nombreUsuario);
    List<Orden> findByUsuarioFreelancer(String nombreUsuario);
}
