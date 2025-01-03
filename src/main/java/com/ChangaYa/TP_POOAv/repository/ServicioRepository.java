package com.ChangaYa.TP_POOAv.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import com.ChangaYa.TP_POOAv.model.Servicio;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;


@Repository
public interface ServicioRepository extends MongoRepository<Servicio, ObjectId> {
    Optional<Servicio> findByid(ObjectId id);
    Optional<Servicio> findByTitulo(String titulo);
    Servicio save(ServicioDto servicio);
    @Query("{ 'titulo': { $regex: ?0, $options: 'i' } }")
    List<Servicio> searchServicios(String query);


}

