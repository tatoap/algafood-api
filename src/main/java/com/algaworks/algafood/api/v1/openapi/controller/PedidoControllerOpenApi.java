package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import com.algaworks.algafood.core.springdoc.PedidosParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {
	
	@Operation(summary = "Lista os pedidos com paginação")
	@PedidosParameter
	@PageableParameter
	PagedModel<PedidoResumoModel> pesquisar(@Parameter(hidden = true) PedidoFilter filtro, @Parameter(hidden = true) Pageable pageable);
	
	@Operation(summary = "Busca um pedido por código", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	PedidoModel buscar(
			@Parameter(description = "Código de um pedido", example = "5c621c9a-ba61-4454-8631-8aabefe58dc2", required = true) 
				String codigoPedido);
	
	@Operation(summary = "Registra um pedido", responses = {
			@ApiResponse(responseCode = "201", description = "Pedido registrado")
	})
	PedidoModel adicionar(
			@Parameter(description = "Representação de um novo pedido", required = true) 
				PedidoInput pedidoInput);
	
}