package com.hospital_vm.cl.hospital_vm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name= "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    @Id // Le indico con el decorador que este sera el id de mi tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Con esto le digo que sera autoincrementable
    private Integer id;

    @Column(unique=true, length = 13, nullable=false)//Con esto le digo que solo es un dato de columna
    private String run;

    @Column(nullable=false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = true)
    private Date fechaNacimiento;

    @Column(nullable=false)
    private String correo;
}
