package com.ChangaYa.TP_POOAv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Document(collection = "categorias")


public class Categoria {
    @Id
    private  ObjectId id;
    private String nombreCategoria;
    private String descripcion;
}
