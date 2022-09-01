package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.domain.model.StatusPedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {
	
	@Schema(example = "5c621c9a-ba61-4454-8631-8aabefe58dc2")
	private String codigo;
	
	@Schema(example = "100.00")
	private BigDecimal subtotal;
	
	@Schema(example = "15.00")
	private BigDecimal taxaFrete;
	
	@Schema(example = "200.00")
	private BigDecimal valorTotal;
	
	@Schema(example = "CRIADO")
	private StatusPedido status;
	
	@Schema(example = "2019-11-02T20:34:04Z")
	private OffsetDateTime dataCriacao;
	
	private RestauranteApenasNomeModel restaurante;
	
	private UsuarioModel cliente;
	
}
