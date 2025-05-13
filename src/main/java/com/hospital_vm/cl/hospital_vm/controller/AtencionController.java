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
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<?> getAtentiontById(@PathVariable Integer id){
        return atencionService.getAtentiontById(id)
            .map(atencion -> ResponseEntity.ok(atencion))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AtencionDTO atencionDTO) {
        try{

            Atencion nuevaAtencion = atencionService.save(atencionDTO);
            return ResponseEntity.ok(nuevaAtencion);

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message","Error de restriccion: " + e.getMostSpecificCause().getMessage()));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest()
                .body(Map.of("message",e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAtention(@PathVariable Integer id,@Valid @RequestBody AtencionDTO atencionDTO){
        try{

            //1. Verificar que la atención exista
            Atencion atencionExistente = atencionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Atención no encontrada"));

            //2. Buscar paciente (sin validaciones adicionales)
            Paciente paciente = pacienteRepository.findById(atencionDTO.getId_paciente())
                .orElseThrow(()-> new RuntimeException("Paciente no encontrado"));
                
            //3. Actualizar campos
            atencionExistente.setFecha_atencion(atencionDTO.getFecha_atencion());
            atencionExistente.setHora_atencion(atencionDTO.getHora_atencion());
            atencionExistente.setCosto(atencionDTO.getCosto());
            atencionExistente.setComentario(atencionDTO.getComentario());
            atencionExistente.setPaciente(paciente);

            //4. Guardar cambios
            Atencion atencionActualizada = atencionRepository.save(atencionExistente);
            return ResponseEntity.ok(atencionActualizada);

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)            
                .body(Map.of("message","Error de restricción: " + e.getMostSpecificCause().getMessage()));
                //¿Qué hace Map.of() en el código?
                //Es una forma compacta y moderna (introducida en Java 9) de crear un Map (diccionario) inmutable
                //con pares clave-valor. En tu método, se usa para construir 
                //respuestas de error estructuradas.
        }catch(RuntimeException e){
            //¿Qué hace Map.of() en el código?
            //Es una forma compacta y moderna (introducida en Java 9) de crear un Map (diccionario) inmutable
            //con pares clave-valor. En tu método, se usa para construir 
            //respuestas de error estructuradas.
            return ResponseEntity.badRequest()
                .body(Map.of("message",e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        if(!atencionService.existeAtencion(id)){
            return ResponseEntity.notFound().build();
        }

        atencionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> eliminarAtencion(@PathVariable int id) {
    //     return atencionService.obtenerAtencion(id)
    //         .map(atencion -> {
    //             atencionService.eliminarAtencion(id);
    //             return ResponseEntity.noContent().build();
    //         })
    //         .orElse(ResponseEntity.notFound().build());
    // }

}

