package com.ChangaYa.TP_POOAv.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Document(collection = "reviews")

public class Review {
    @Id
    private ObjectId id;
    private String nombreCliente;
    private String tituloServicio;
    private String comentario;
    private int calificacion; 
    @CreationTimestamp
    private LocalDate fechaCalificacion;
}
