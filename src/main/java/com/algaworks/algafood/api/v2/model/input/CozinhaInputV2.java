package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputV2 {
	
	//@ApiModelProperty(example = "Mineira", required = true)
	@NotBlank
	private String nomeCozinha;

}
