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

import com.innovation.growact.model.Tarefa;
import com.innovation.growact.repository.ObjetivoRepository;
import com.innovation.growact.repository.TarefaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TarefaController {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private ObjetivoRepository objetivoRepository;
	
	@GetMapping
	public ResponseEntity<List<Tarefa>> getAll(){
		return ResponseEntity.ok(tarefaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tarefa> getById(@PathVariable Long id){
		return tarefaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping
	public ResponseEntity<Tarefa> post (@Valid @RequestBody Tarefa tarefa){
	    if(objetivoRepository.existsById(tarefa.getObjetivo().getId()))
	    	return ResponseEntity.status(HttpStatus.CREATED)
	    			.body(tarefaRepository.save(tarefa));
	    
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não objetivo para essa tarefa", null);
	}
	
	
	@PutMapping 
	public ResponseEntity<Tarefa> put (@Valid @RequestBody Tarefa tarefa){
		if(tarefaRepository.existsById(tarefa.getId())) {
			if(objetivoRepository.existsById(tarefa.getObjetivo().getId()))
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(tarefaRepository.save(tarefa));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não objetivo para essa tarefa", null);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		Optional<Tarefa> tarefa = tarefaRepository.findById(id);
		
		if(tarefa.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} else {
			tarefaRepository.deleteById(id);
		}
	}
	
	
}
