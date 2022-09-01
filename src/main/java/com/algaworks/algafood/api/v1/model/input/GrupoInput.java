package com.algaworks.algafood.api.v1.model.input;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {
	
	@Schema(example = "RH")
	@NotBlank
	private String nome;
	
	private List<PermissaoInput> permissoes;
	
}
