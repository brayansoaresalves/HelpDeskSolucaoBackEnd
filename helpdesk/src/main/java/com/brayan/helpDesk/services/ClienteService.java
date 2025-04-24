package com.brayan.helpDesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brayan.helpDesk.domain.Cliente;
import com.brayan.helpDesk.domain.Pessoa;
import com.brayan.helpDesk.domain.dto.ClienteDTO;
import com.brayan.helpDesk.domain.exceptions.DataIntegroVioladoException;
import com.brayan.helpDesk.domain.exceptions.ObjectNotFoundException;
import com.brayan.helpDesk.repositories.ClienteRepository;
import com.brayan.helpDesk.repositories.PessoaRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Transactional
	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		validaPorCpfEEmail(clienteDTO);
		Cliente novoCliente = new Cliente(clienteDTO);
		return clienteRepository.save(novoCliente);
	}

	private void validaPorCpfEEmail(ClienteDTO clienteDTO) {
		Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByCpf(clienteDTO.getCpf());
		if (pessoaEncontrada.isPresent() && pessoaEncontrada.get().getId() != clienteDTO.getId()) {
			throw new DataIntegroVioladoException("CPF já cadastrado no sistema");
		}
		
		pessoaEncontrada = pessoaRepository.findByEmail(clienteDTO.getEmail());
		
		if (pessoaEncontrada.isPresent() && pessoaEncontrada.get().getId() != clienteDTO.getId()) {
			throw new DataIntegroVioladoException("E-mail já cadastrado no sistema");
		}
		
	}

	@Transactional
	public Cliente update(ClienteDTO clienteDTO, Integer id) {
		Cliente clienteEncontrado = findById(id);
		validaPorCpfEEmail(clienteDTO);
		clienteEncontrado = new Cliente(clienteDTO);
		return clienteRepository.save(clienteEncontrado);
	}

	@Transactional
	public void delete(Integer id) {
		Cliente clienteEncontrado = findById(id);
		if (clienteEncontrado.getChamados().size() > 0) {
			throw new DataIntegroVioladoException("Cliente possui ordem de serviço e não pode ser deletado!!");
		}
		
		clienteRepository.delete(clienteEncontrado);
		
	}
	


}
