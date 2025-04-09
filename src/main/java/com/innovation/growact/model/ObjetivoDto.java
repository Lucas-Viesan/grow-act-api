package com.innovation.growact.model;

import java.time.LocalDate;
import java.util.List;

public class ObjetivoDto {
	private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private double percentual;

    public ObjetivoDto(Objetivo objetivo) {
        this.id = objetivo.getId();
        this.titulo = objetivo.getTitulo();
        this.descricao = objetivo.getDescricao();
        this.dataCriacao = objetivo.getData();

        List<Tarefa> tarefas = objetivo.getTarefas();
        if (tarefas == null || tarefas.isEmpty()) {
            this.percentual = 0.0;
        } else {
            long concluidas = tarefas.stream().filter(Tarefa::getConcluida).count();
            this.percentual = (double) concluidas / tarefas.size() * 100;
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public double getPercentual() {
		return percentual;
	}

	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}

    
}

