package com.innovation.growact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovation.growact.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
}
