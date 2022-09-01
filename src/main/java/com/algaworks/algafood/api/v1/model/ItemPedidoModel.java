package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
	
	@Schema(example = "3")
	private long produtoId;
	
	@Schema(example = "Salada picante com carne grelhada")
	private String produtoNome;
	
	@Schema(example = "2")
	private Integer quantidade;
	
	@Schema(example = "100.00")
	private BigDecimal precoUnitario;
	
	@Schema(example = "200.00")
	private BigDecimal precoTotal;
	
	@Schema(example = "Com pouca pimenta")
	private String observacao;
	
}
