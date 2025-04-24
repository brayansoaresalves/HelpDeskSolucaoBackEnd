package com.brayan.helpDesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brayan.helpDesk.domain.Chamado;
import com.brayan.helpDesk.domain.Cliente;
import com.brayan.helpDesk.domain.Tecnico;
import com.brayan.helpDesk.domain.enums.Perfil;
import com.brayan.helpDesk.domain.enums.Prioridade;
import com.brayan.helpDesk.domain.enums.Status;
import com.brayan.helpDesk.repositories.ChamadoRepository;
import com.brayan.helpDesk.repositories.ClienteRepository;
import com.brayan.helpDesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "668.939.370-40", "valdir@gmail.com", encoder.encode("123456"));
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Brayan Alves", "436.757.090-86", "brayan.iub10@gmail.com", encoder.encode("12345678"));
		cli1.addPerfil(Perfil.CLIENTE);
		
		Chamado chamado = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Chamado o1", "Primeiro Chamado", tec1, cli1);
		
		tecnicoRepository.save(tec1);
		clienteRepository.save(cli1);
		chamadoRepository.save(chamado);
	}

}
