package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {
	
	@Schema(example = "Recursos Humanos")
	@NotBlank
	private String nome;
	
	@Schema(example = "Grupo dos funcionários de RH (não obrigatório)")
	private String descricao;
}
