package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@Operation(summary = "Lista as formas de pagamento associadas a um restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CollectionModel<FormaPagamentoModel> listar(
			@Parameter(description = "Id de um restaurante", example = "1", required = true) 
				Long restauranteId);
	
	@Operation(summary = "Desassocia uma forma de pagamento de um restaurante", responses = {
			@ApiResponse(responseCode = "204", description = "Forma de pagamento desassociada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou forma de pagamento não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> desassociar(
			@Parameter(description = "Id do restaurante", example = "1", required = true) 
				Long restauranteId, 
			@Parameter(description = "Id da forma de pagamento", example = "1", required = true)
				Long formaPagamentoId);
	
	@Operation(summary = "Associa uma forma de pagamento para um restaurante", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou forma de pagamento não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> associar(
			@Parameter(description = "Id do restaurante", example = "1", required = true) 
				Long restauranteId, 
			@Parameter(description = "Id da forma de pagamento", example = "1", required = true)
				Long formaPagamentoId);
	
}
