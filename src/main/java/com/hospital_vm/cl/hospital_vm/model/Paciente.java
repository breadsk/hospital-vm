package com.hospital_vm.cl.hospital_vm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

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

    @Column(name="rut", unique=true, length = 13, nullable=false)//Con esto le digo que solo es un dato de columna
    @NotBlank(message = "RUT es obligatorio")
    @Pattern(
        regexp = "^[0-9]{7,8}-[0-9Kk]$",
        message = "El rut debe tener formato 12345678-9 ( Chile )"
    )
    private String rut;

    @Column(nullable=false)
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min=2,max=100,message="Los apellidos deben tener entre 2 y 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min=2,max=100,message="Los apellidos deben tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = true)
    @Past(message = "La fecha de nacimiento debe ser en el pasado")//Solo si nullable = true
    private Date fechaNacimiento;

    @Column(nullable=false,unique=true)
    @NotBlank(message="El correo es obligatorio")
    @Email(message="El formato del correo no es válido")
    private String correo;
}
