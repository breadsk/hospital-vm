package com.hospital_vm.cl.hospital_vm.repository;

import com.hospital_vm.cl.hospital_vm.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    // No necesitas agregar ningun método, JpaRepository ya tiene findAll() implementado


    //Usando JPQL
    //@Query("SELECT p FROM Paciente p")
    //List<Paciente> findAll();
    //No necesitas definir el método findAll(), 
}
