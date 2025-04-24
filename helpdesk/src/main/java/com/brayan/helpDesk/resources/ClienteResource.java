package com.brayan.helpDesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brayan.helpDesk.domain.Cliente;
import com.brayan.helpDesk.domain.dto.ClienteDTO;
import com.brayan.helpDesk.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;

	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CLIENTE', 'ADMIN', 'TECNICO')")
	public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id){
		Cliente cliente = clienteService.findById(id);
		return ResponseEntity.ok(new ClienteDTO(cliente));
	}
	
	@GetMapping
	@PreAuthorize("hasRole('CLIENTE', 'ADMIN', 'TECNICO')")
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listaDTO = clienteService.findAll().stream().map(t -> new ClienteDTO(t)).collect(Collectors.toList());
		return ResponseEntity.ok(listaDTO);
		
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.create(clienteDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ClienteDTO> atualizar(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
		Cliente clienteFiltrado = clienteService.update(clienteDTO, id);
		return ResponseEntity.ok(new ClienteDTO(clienteFiltrado));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
