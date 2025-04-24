package com.brayan.helpDesk.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.brayan.helpDesk.domain.dto.ClienteDTO;
import com.brayan.helpDesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Chamado> chamados = new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.CLIENTE);
	}
	
	public Cliente(ClienteDTO clienteDTO) {
		super();
		this.id = clienteDTO.getId();
		this.cpf = clienteDTO.getCpf();
		this.nome = clienteDTO.getNome();
		this.email = clienteDTO.getEmail();
		this.senha = clienteDTO.getSenha();
		this.dataCriacao = clienteDTO.getDataCriacao();
		this.perfis = clienteDTO.getPerfis().stream().map(t -> t.getCodigo()).collect(Collectors.toSet());
	}
	
	
	public List<Chamado> getChamados() {
		return chamados;
	}
	
	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
}
