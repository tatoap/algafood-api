package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@Schema(example = "Patricia")
	@NotBlank	
	private String nome;
	
	@Schema(example = "patricia@outlook.com")
	@Email
	@NotBlank
	private String email;
}
