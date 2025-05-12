package com.hospital_vm.cl.hospital_vm.controller;

import com.hospital_vm.cl.hospital_vm.model.Atencion;
import com.hospital_vm.cl.hospital_vm.model.Paciente;
import com.hospital_vm.cl.hospital_vm.service.AtencionService;
import com.hospital_vm.cl.hospital_vm.repository.AtencionRepository;
import com.hospital_vm.cl.hospital_vm.repository.PacienteRepository;

import com.hospital_vm.cl.hospital_vm.dto.AtencionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

//import javax.xml.validation.;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.LocalTime;

//import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/atenciones")
public class AtencionController {

    @Autowired
    private AtencionService atencionService;
    @Autowired
    private AtencionRepository atencionRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public String inicio() {
        return "Hola";
    }
    

    @GetMapping("/listar")
    public List<Atencion> getAllAtentions(){            
        return atencionService.findAll();
    }
   
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id){
        Optional<Atencion> atencion = atencionService.getAtentiontById(id);

        if(atencion.isPresent()){
            //Respuesta exitosa con cabeceras personalizadas (opcional)
            return ResponseEntity.ok()
                    .header("mi-encabezado", "valor")
                    .body(atencion.get());
        } else {
            //Respuesta de error con cuerpo personalizado ( ej: JSON con mensaje)
            Map<String,String> errorBody = new HashMap<>();
            errorBody.put("message","No se encontró la atencion con ID: " + id);
            errorBody.put("status","404");
            errorBody.put("timestamp",LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorBody);
        }

        //Se usa <?> para permitir que el método retorne:
        //1. Un objeto Paciente ( en caso de éxito, código 200)
        //2. Un objeto de error personalizado(en caso de fallo, código 404)

        //Al usar <?>, no estas limitando el cuerpo de la respuesta a un único tipo (como Paciente)
        //Si no que permites múltiples tipos en la respuesta
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AtencionDTO atencionDTO){
        try{

            Paciente paciente = pacienteRepository.findById(atencionDTO.getId_paciente())
                                        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

            //Convertir DTO a Entidad
            Atencion atencion = new Atencion();
            atencion.setFecha_atencion(atencionDTO.getFecha_atencion());
            atencion.setCosto(atencionDTO.getCosto());
            atencion.setPaciente(paciente);
            atencion.setComentario(atencionDTO.getComentario());

            //Guardar
            Atencion nuevaAtencion = atencionRepository.save(atencion);

            return ResponseEntity.ok(nuevaAtencion);

            
        } catch(DataIntegrityViolationException e){
            //Ejemplo: Error si hay un campo único duplicado (ej: email repetido)
            Map<String,String> error = new HashMap<>();
            error.put("message","El email ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//Error 409
        }
        
    }
    
    // @PutMapping("/{id}")
    // public ResponseEntity<Atencion> update(@PathVariable int id,@RequestBody Atencion atencion){
    //     try{

    //         Paciente paciente = 

    //         Atencion ate = atencionService.getPatientById2(id);
    //         ate.setId_atencion(id);
    //         ate.setFecha_atencion(atencion.getFecha_atencion());
    //         ate.setHora_atencion(atencion.getHora_atencion());
    //         ate.setCosto(atencion.getCosto());
    //         ate.setPaciente(null);

    //         atencionService.save(atencion);
    //         return ResponseEntity.ok(atencion);

    //     }catch(Exception ex){
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id){
        try{

            atencionService.delete(id);
            return ResponseEntity.noContent().build();

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

}

