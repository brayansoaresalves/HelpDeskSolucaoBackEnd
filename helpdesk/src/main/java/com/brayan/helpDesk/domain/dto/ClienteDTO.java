package com.brayan.helpDesk.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.brayan.helpDesk.domain.Cliente;
import com.brayan.helpDesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotBlank(message = "O campo nome é requirido")
	protected String nome;
	
	@NotBlank(message = "O campo CPF é requirido")
	protected String cpf;
	
	@NotBlank(message = "O campo E-mail é requirido")
	protected String email;
	
	@NotBlank(message = "O campo senha é requirido")
	protected String senha;
	
	protected Set<Integer> perfis = new HashSet<>();
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();
	
	public ClienteDTO() {
		super();
		addPerfis(Perfil.CLIENTE);
	}
	
	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.cpf = cliente.getCpf();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.senha = cliente.getSenha();
		this.dataCriacao = cliente.getDataCriacao();
		this.perfis = cliente.getPerfis().stream().map(t -> t.getCodigo()).collect(Collectors.toSet());
		addPerfis(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet());
	}

	public void addPerfis(Perfil perfil) {
		perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	

}
