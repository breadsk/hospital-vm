package com.hospital_vm.cl.hospital_vm.service;

import com.hospital_vm.cl.hospital_vm.model.Atencion;
import com.hospital_vm.cl.hospital_vm.repository.AtencionRepository;
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

    public Atencion save(Atencion atencion){
        return atencionRepository.save(atencion);
    }

    public void delete(int id){
        atencionRepository.deleteById(id);
    }
}

