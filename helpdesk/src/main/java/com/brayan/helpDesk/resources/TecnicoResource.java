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

import com.brayan.helpDesk.domain.Tecnico;
import com.brayan.helpDesk.domain.dto.TecnicoDTO;
import com.brayan.helpDesk.services.TecnicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CLIENTE', 'ADMIN', 'TECNICO')")
	public ResponseEntity<TecnicoDTO> buscarPorId(@PathVariable Integer id){
		Tecnico tecnico = tecnicoService.findById(id);
		return ResponseEntity.ok(new TecnicoDTO(tecnico));
	}
	
	@GetMapping
	@PreAuthorize("hasRole('CLIENTE', 'ADMIN', 'TECNICO')")
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<TecnicoDTO> listaDTO = tecnicoService.findAll().stream().map(t -> new TecnicoDTO(t)).collect(Collectors.toList());
		return ResponseEntity.ok(listaDTO);
		
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
		Tecnico tecnico = tecnicoService.create(tecnicoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tecnico.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TecnicoDTO> atualizar(@Valid @RequestBody TecnicoDTO tecnicoDTO, @PathVariable Integer id){
		Tecnico tecnicoFiltrado = tecnicoService.update(tecnicoDTO, id);
		return ResponseEntity.ok(new TecnicoDTO(tecnicoFiltrado));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		tecnicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
