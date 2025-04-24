package com.brayan.helpDesk.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.brayan.helpDesk.domain.dto.TecnicoDTO;
import com.brayan.helpDesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Tecnico extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tecnico")
	private List<Chamado> chamados = new ArrayList<>();

	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO);
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO);
	}
	
	public Tecnico(TecnicoDTO tecnicoDTO) {
		super();
		this.id = tecnicoDTO.getId();
		this.cpf = tecnicoDTO.getCpf();
		this.nome = tecnicoDTO.getNome();
		this.email = tecnicoDTO.getEmail();
		this.senha = tecnicoDTO.getSenha();
		this.dataCriacao = tecnicoDTO.getDataCriacao();
		this.perfis = tecnicoDTO.getPerfis().stream().map(t -> t.getCodigo()).collect(Collectors.toSet());
	}
	
	public List<Chamado> getChamados() {
		return chamados;
	}
	
	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

}
