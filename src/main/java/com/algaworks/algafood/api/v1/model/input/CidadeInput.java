package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@Schema(example = "Uberlândia")
	@NotBlank
	private String nome;
	
	@NotNull
	@Valid
	private EstadoIdInput estado;
	
}
