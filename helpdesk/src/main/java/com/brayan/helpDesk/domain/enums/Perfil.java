package com.brayan.helpDesk.domain.enums;

public enum Perfil {

	ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");
	
	private Integer codigo;
	private String descricao;
	
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (Perfil perfil : Perfil.values()) {
			if (codigo.equals(perfil.getCodigo())) {
				return perfil;
			}
		}
		
		throw new IllegalArgumentException("Perfil inválido");
	}
	
	
}
