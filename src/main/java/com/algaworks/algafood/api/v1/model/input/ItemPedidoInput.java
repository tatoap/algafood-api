package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
	
	@Schema(example = "3")
	@NotNull
	private Long produtoId;
	
	@Schema(example = "2")
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	private String observacao;
	
}
