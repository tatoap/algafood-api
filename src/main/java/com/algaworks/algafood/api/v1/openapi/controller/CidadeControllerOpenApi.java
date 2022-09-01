package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Cidades")
@SecurityRequirement(name = "security_auth")
public interface CidadeControllerOpenApi {

	@Operation(summary = "Lista as cidades")
	CollectionModel<CidadeModel> listar();
	
	@Operation(summary = "Cadastra uma cidade", responses = {
			@ApiResponse(responseCode = "201", description = "Cidade cadastrada")
	})
	CidadeModel adicionar(
			@Parameter(description = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);
	
	@Operation(summary = "Busca uma cidade por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id da cidade inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CidadeModel buscar(
			@Parameter(description = "Id de uma cidade", example = "1", required = true) Long cidadeId);
		
	@Operation(summary = "Atualiza uma cidade por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Cidade atualizada"),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CidadeModel atualizar(
			@Parameter(description = "Id de uma cidade", example = "1", required = true) Long cidadeId, 
			@Parameter(description = "Representação de uma cidade com os novos dados", example = "1", required = true) 
				CidadeInput cidadeInput);
	
	@Operation(summary = "Exclui uma cidade por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Cidade excluída"),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(
			@Parameter(description = "Id de uma cidade", example = "1", required = true) Long cidadeId);

}

