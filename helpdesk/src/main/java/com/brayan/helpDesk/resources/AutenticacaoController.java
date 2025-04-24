package com.brayan.helpDesk.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brayan.helpDesk.domain.dto.CredenciaisDTO;
import com.brayan.helpDesk.security.JWTUtil;
import com.brayan.helpDesk.security.UserSS;
import com.brayan.helpDesk.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AutenticacaoController {

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

    AutenticacaoController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
    @PostMapping
	public ResponseEntity<?> autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
		try {
			
			UserSS usuario = (UserSS) userDetailsService.loadUserByUsername(credenciaisDTO.getEmail());
			
			if (usuario != null && passwordEncoder.matches(credenciaisDTO.getSenha(), usuario.getPassword())){
				String token = jwtUtil.generateToken(usuario.getUsername());
				
				return ResponseEntity.ok(token);
			}else {
				throw new RuntimeException("Usuário ou senha invalido");
			}
			
		}catch (Exception e) {
			throw new RuntimeException("Usuário ou senha invalido");
		}
	}

}
