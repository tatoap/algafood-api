package com.algaworks.algafood.api.v1.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoFotoControllerOpenApi {

	@Operation(summary = "Atualiza a foto do produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não localizado", content = @Content(schema = @Schema))
	})
	FotoProdutoModel atualizarFoto(
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId, 
		@Parameter(description = "Id do produto", example = "1", required = true)
			Long produtoId, 
		@RequestBody(required = true)	
			FotoProdutoInput fotoProdutoInput) throws IOException;
	
	@Operation(summary = "Exclui a foto do produto de um restaurante", responses = {
			@ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
			@ApiResponse(responseCode = "400", description = "Id do restaurante e/ou produto inválida", content = @Content(schema = @Schema)),
			@ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = @Content(schema = @Schema))
	})
	void remover(
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId, 
		@Parameter(description = "Id do produto", example = "1", required = true)
			Long produtoId);
	
	@Operation(summary = "Busca a foto do produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
					@Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
					@Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
			}),
			@ApiResponse(responseCode = "400", description = "Id do restaurante e/ou produto inválido", content = @Content(schema = @Schema)),
			@ApiResponse(responseCode = "404", description = "Foto do produto não encontrada", content = @Content(schema = @Schema))
	})
	FotoProdutoModel buscar(
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId, 
		@Parameter(description = "Id do produto", example = "1", required = true)
			Long produtoId);
	
	@Operation(summary = "Busca a foto do produto de um restaurante", hidden = true)
	ResponseEntity<?> servir(Long restauranteId, Long produtoId, String acceptHeader) 
			throws HttpMediaTypeNotAcceptableException;
	
}
