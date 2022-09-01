package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Estados")
@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {
	
	@Operation(summary = "Lista os estados")
	CollectionModel<EstadoModel> listar();
	
	@Operation(summary = "Busca um estado por Id", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id do estado inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	EstadoModel buscar(
			@Parameter(description = "Id de um estado", example = "1", required = true) 
				Long estadoId);
	
	@Operation(summary = "Adiciona um estado", responses = {
			@ApiResponse(responseCode = "200", description = "Estado cadastrado")
	})
	EstadoModel adicionar(
			@Parameter(description = "Representação de um novo estado", required = true) EstadoInput estadoInput);
	
	@Operation(summary = "Atualiza um estado", responses = {
			@ApiResponse(responseCode = "200", description = "Estado atualizado"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	EstadoModel atualizar(
			@Parameter(description = "Id de um estado", required = true)
				Long estadoId, 
			@Parameter(description = "Representação de um estado com os novos dados", required = true)
				EstadoInput estadoInput);

	@Operation(summary = "Exclui um estado por Id", responses = {
			@ApiResponse(responseCode = "204", description = "Estado excluído"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(
			@Parameter(description = "Id de um estado", example = "1", required = true) Long estadoId);

}
