package com.brayan.helpDesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brayan.helpDesk.domain.Pessoa;
import com.brayan.helpDesk.repositories.PessoaRepository;
import com.brayan.helpDesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Pessoa> usuario = pessoaRepository.findByEmail(username);
		if (usuario.isPresent()) {
			return new UserSS(usuario.get().getId(), usuario.get().getEmail(), usuario.get().getSenha(), usuario.get().getPerfis());
		}
		
		throw new UsernameNotFoundException(username);
	}

}
