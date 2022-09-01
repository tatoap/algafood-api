package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {
	
	@Schema(example = "1234")
	@NotBlank	
	private String senhaAtual;
	
	@Schema(example = "5678")
	@NotBlank
	private String novaSenha;
}
