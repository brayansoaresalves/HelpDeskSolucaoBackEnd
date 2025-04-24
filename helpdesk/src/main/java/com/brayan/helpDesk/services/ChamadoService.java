package com.brayan.helpDesk.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brayan.helpDesk.domain.Chamado;
import com.brayan.helpDesk.domain.Cliente;
import com.brayan.helpDesk.domain.Tecnico;
import com.brayan.helpDesk.domain.dto.ChamadoDTO;
import com.brayan.helpDesk.domain.enums.Prioridade;
import com.brayan.helpDesk.domain.enums.Status;
import com.brayan.helpDesk.domain.exceptions.ObjectNotFoundException;
import com.brayan.helpDesk.repositories.ChamadoRepository;

import jakarta.validation.Valid;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		return chamadoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}

	@Transactional
	public Chamado create(@Valid ChamadoDTO chamadoDTO) {
		return chamadoRepository.save(novoChamado(chamadoDTO));
	}
	
	private Chamado novoChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if (obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}

	@Transactional
	public Chamado update(Integer id, @Valid ChamadoDTO chamadoDTO) {
		chamadoDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = novoChamado(chamadoDTO);
		return chamadoRepository.save(oldObj);
	}
		

}
