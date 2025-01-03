package com.ChangaYa.TP_POOAv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import org.bson.types.ObjectId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Document(collection = "ordenes")

public class Orden {
    @Id
    private ObjectId id;
    @Id
    private String tituloServicio;
    @Id
    private String usuarioCliente;
    private double precioServicio;
    @CreationTimestamp
    private LocalDate fechaOrden;
    private EstadoOrden estadoOrden;
    private LocalDate fechaEntrega;
    private String usuarioFreelancer;
    
   


}
