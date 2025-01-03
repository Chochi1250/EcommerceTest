package com.ChangaYa.TP_POOAv.repository;

import java.util.Optional;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ChangaYa.TP_POOAv.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review,ObjectId> {

    Optional<Review> findByid(ObjectId id);
    List<Review> findByTituloServicio(String tituloServicio);

    
}
