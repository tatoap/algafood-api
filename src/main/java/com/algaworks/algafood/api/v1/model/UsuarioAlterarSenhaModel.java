package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAlterarSenhaModel {

	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "1234")
	private String senha;
	
}
