package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Grupos")
@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {

	@Operation(summary = "Lista os grupos")
	CollectionModel<GrupoModel> listar();
	
	@Operation(summary = "Adiciona um grupo", responses = {
			@ApiResponse(responseCode = "201", description = "Grupo cadastrado")
	})
	GrupoModel adicionar(
			@Parameter(description = "Representação de um novo grupo", required = true)
				GrupoInput grupoInput);
	
	@Operation(summary = "Busca um grupo por Id", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id de grupo inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	GrupoModel buscar(
			@Parameter(description = "Id de um grupo", example = "1", required = true) Long grupoId);
	
	@Operation(summary = "Atualiza um grupo por Id", responses = {
			@ApiResponse(responseCode = "200", description = "Grupo atualizado"),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	GrupoModel atualizar(
			@Parameter(description = "Id de um grupo", example = "1", required = true) 
				Long grupoId, 
			@Parameter(description = "Representação de um grupo com os novos dados", example = "1", required = true)
				GrupoInput grupoInput);	
	
	@Operation(summary = "Exclui um grupo por Id", responses = {
			@ApiResponse(responseCode = "204", description = "Grupo excluído"),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(
			@Parameter(description = "Id de um grupo", example = "1", required = true) Long grupoId);
	
}