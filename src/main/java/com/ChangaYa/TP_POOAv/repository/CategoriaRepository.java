package com.ChangaYa.TP_POOAv.repository;

import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.ChangaYa.TP_POOAv.model.Categoria;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria,ObjectId> {
    Optional<Categoria> findByNombreCategoria(ObjectId id);
}
