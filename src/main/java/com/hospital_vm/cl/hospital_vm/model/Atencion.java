package com.hospital_vm.cl.hospital_vm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;//Recomendado para versiones Java +8

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "atencion")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_atencion;

    
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_atencion;
    
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hora_atencion;

    @Column(nullable = false)
    @Positive
    private Double costo;

    @ManyToOne
    @JoinColumn(name="id_paciente", nullable = false)
    @JsonIgnoreProperties("paciente")
    private Paciente paciente;

    @Column(nullable = false)
    private String comentario;


}
