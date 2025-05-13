package com.hospital_vm.cl.hospital_vm.service;

import com.hospital_vm.cl.hospital_vm.model.Atencion;
}import com.hospital_vm.cl.hospital_vm.model.Paciente;
import com.hospital_vm.cl.hospital_vm.repository.AtencionRepository;
import com.hospital_vm.cl.hospital_vm.repository.PacienteRepository;
import com.hospital_vm.cl.hospital_vm.dto.AtencionDTO;//DTO
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Atencion> findAll(){
        return atencionRepository.findAll();
    }

    public Optional<Atencion> getAtentiontById(Integer id){
        return atencionRepository.findById(id);
    }

    public Atencion getPatientById2(int id){
        return atencionRepository.findById(id).get();
    }

    //La funcion save funciona tanto como para crear o actualizar

    public Atencion save(AtencionDTO atencionDTO){
        //1. Buscar paciente
        Paciente paciente = pacienteRepository.findById(atencionDTO.getId_paciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        //2. Crear y guardar atención
        Atencion atencion = new Atencion();
        atencion.setFecha_atencion(atencionDTO.getFecha_atencion());
        atencion.setHora_atencion(atencionDTO.getHora_atencion());
        atencion.setCosto(atencionDTO.getCosto());        
        atencion.setComentario(atencionDTO.getComentario());
        atencion.setPaciente(paciente);

        return atencionRepository.save(atencion);
    }

    public void delete(int id){
        atencionRepository.deleteById(id);
    }
}

