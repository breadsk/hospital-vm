package com.hospital_vm.cl.hospital_vm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.time.LocalTime;//Recomendado para versiones Java +8

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @DateTimeFormat(pattern = "ddd-MM-yyyy")
    private Date fecha_atencion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
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
