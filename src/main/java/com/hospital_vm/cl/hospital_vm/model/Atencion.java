package com.hospital_vm.cl.hospital_vm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "atencion")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_atencion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha_atencion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date hora_atencion;

    @Column(nullable = false)
    @Positive
    private Double costo;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private String comentario;


}
