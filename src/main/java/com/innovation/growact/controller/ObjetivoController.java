package com.innovation.growact.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.innovation.growact.model.Objetivo;
import com.innovation.growact.repository.ObjetivoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/objetivos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ObjetivoController {
	
	@Autowired
	private ObjetivoRepository objetivoRepository;
	
	@GetMapping
	public ResponseEntity<List<Objetivo>> getAll(){
		return ResponseEntity.ok(objetivoRepository.findAll());
    }
		
    @PostMapping
    public ResponseEntity<Objetivo> post(@Valid @RequestBody Objetivo objetivo){
    	return ResponseEntity.status(HttpStatus.CREATED)
    			.body(objetivoRepository.save(objetivo));
    }
	
    @PutMapping
    public ResponseEntity<Objetivo> put(@Valid @RequestBody Objetivo objetivo){
    	return objetivoRepository.findById(objetivo.getId())
    			.map(resposta -> ResponseEntity.status(HttpStatus.OK)
    					.body(objetivoRepository.save(resposta)))
    			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
    	Optional<Objetivo> objetivo = objetivoRepository.findById(id);
    	
    	if(objetivo.isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    	} else {
    		objetivoRepository.deleteById(id);
    	}
    }
}


