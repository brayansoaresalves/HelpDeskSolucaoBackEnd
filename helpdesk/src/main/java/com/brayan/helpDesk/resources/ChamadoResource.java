package com.brayan.helpDesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brayan.helpDesk.domain.Chamado;
import com.brayan.helpDesk.domain.dto.ChamadoDTO;
import com.brayan.helpDesk.services.ChamadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService service;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN', 'TECNICO', 'CLIENTE')")
	public ResponseEntity<ChamadoDTO> buscarPorId(@PathVariable Integer id){
		Chamado chamado = service.findById(id);
		return ResponseEntity.ok(new ChamadoDTO(chamado));
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN', 'TECNICO', 'CLIENTE')")
	public ResponseEntity<List<ChamadoDTO>> findAll() {
		List<Chamado> lista = service.findAll();
		return ResponseEntity.ok(lista.stream().map(chamado -> new ChamadoDTO(chamado)).collect(Collectors.toList()));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN', 'TECNICO', 'CLIENTE')")
	public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO){
		Chamado objeto = service.create(chamadoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN', 'TECNICO', 'CLIENTE')")
	public ResponseEntity<ChamadoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO chamadoDTO){
		Chamado newObj = service.update(id, chamadoDTO);
		return ResponseEntity.ok().body(new ChamadoDTO(newObj));
	}
	

}
