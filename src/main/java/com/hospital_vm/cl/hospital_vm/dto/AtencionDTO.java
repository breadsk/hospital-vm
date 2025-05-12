package com.hospital_vm.cl.hospital_vm.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtencionDTO {
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_atencion;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hora_atencion;
    private Double costo;
    private Integer id_paciente;
    private String comentario;
}
