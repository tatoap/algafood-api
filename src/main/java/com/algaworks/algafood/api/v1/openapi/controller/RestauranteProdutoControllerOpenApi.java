package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {
	
	@Operation(summary = "Listagem dos produtos por restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id do restaurante inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CollectionModel<ProdutoModel> listar(
		@Parameter(description = "Id do restaurante", example = "1", required = true) 
			Long restauranteId,
		@Parameter(description = "Indica se deve ou não incluir produtos inativos no resultado da listagem", 
				example = "false")
			Boolean incluirInativos);
	
	@Operation(summary = "Busca um produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id do restaurante inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ProdutoModel buscar(
		@Parameter(description = "Id do produto", example = "1", required = true)
			Long produtoId, 
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId);
	
	@Operation(summary = "Cadastra um produto de um restaurante", responses = {
			@ApiResponse(responseCode = "201", description = "Produto cadastrado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ProdutoModel adicionar(
		@Parameter(description = "Representação de um novo produto", required = true)
			ProdutoInput produtoInput, 
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId);
	
	@Operation(summary = "Atualiza um produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200", description = "Produto atualizado"),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ProdutoModel atualizar(
			@Parameter(description = "Representação de um produto com os novos dados", required = true)
				ProdutoInput produtoInput, 
			@Parameter(description = "Id do produto", example = "1", required = true)
				Long produtoId, 
			@Parameter(description = "Id do restaurante", example = "1", required = true)
				Long restauranteId);

}
