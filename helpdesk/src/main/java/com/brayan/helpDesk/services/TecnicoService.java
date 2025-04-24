package com.brayan.helpDesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brayan.helpDesk.domain.Pessoa;
import com.brayan.helpDesk.domain.Tecnico;
import com.brayan.helpDesk.domain.dto.TecnicoDTO;
import com.brayan.helpDesk.domain.exceptions.DataIntegroVioladoException;
import com.brayan.helpDesk.domain.exceptions.ObjectNotFoundException;
import com.brayan.helpDesk.repositories.PessoaRepository;
import com.brayan.helpDesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	@Transactional
	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		validaPorCpfEEmail(tecnicoDTO);
		Tecnico novoTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(novoTecnico);
	}

	private void validaPorCpfEEmail(TecnicoDTO tecnicoDTO) {
		Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
		if (pessoaEncontrada.isPresent() && pessoaEncontrada.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegroVioladoException("CPF já cadastrado no sistema");
		}
		
		pessoaEncontrada = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
		
		if (pessoaEncontrada.isPresent() && pessoaEncontrada.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegroVioladoException("E-mail já cadastrado no sistema");
		}
		
	}

	@Transactional
	public Tecnico update(TecnicoDTO tecnicoDTO, Integer id) {
		Tecnico tecnicoEncontrado = findById(id);
		validaPorCpfEEmail(tecnicoDTO);
		tecnicoEncontrado = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(tecnicoEncontrado);
	}

	@Transactional
	public void delete(Integer id) {
		Tecnico tecnicoFiltrado = findById(id);
		if (tecnicoFiltrado.getChamados().size() > 0) {
			throw new DataIntegroVioladoException("Técnico possui ordem de serviço e não pode ser deletado!!");
		}
		
		tecnicoRepository.delete(tecnicoFiltrado);
		
	}
	


}
