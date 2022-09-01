package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Cozinhas")
@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

	@Operation(summary = "Lista as cozinhas com paginação")
	@PageableParameter
	PagedModel<CozinhaModel> listar(@Parameter(hidden = true) Pageable pageable);
	
	@Operation(summary = "Busca uma cozinha por Id", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id da cozinha inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CozinhaModel buscar(
			@Parameter(description = "Id de uma cidade", example = "1", required = true) Long cozinhaId);
	
	@Operation(summary = "Cadastra uma cozinha", responses = {
			@ApiResponse(responseCode = "201", description = "Cozinha cadastrada")
	})
	CozinhaModel adicionar(
			@Parameter(description = "Representação de uma nova cozinha", required = true) CozinhaInput cozinhaInput);
	
	@Operation(summary = "Atualiza uma cozinha por Id", responses = {
			@ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CozinhaModel atualizar(
			@Parameter(description = "Id de uma cozinha", example = "1", required = true) Long cozinhaId, 
			@Parameter(description = "Representação de uma cozinha atualizada", required = true) 
				CozinhaInput cozinhaInput);
	
	@Operation(summary = "Exclui uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Cozinha excluida"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(
			@Parameter(description = "Id de uma cozinha", example = "1", required = true) Long cozinhaId);
	
}