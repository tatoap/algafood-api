package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.GrupoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Usuários")
@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {
	
	@Operation(summary = "Lista os grupos associados ao usuário", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id do usuário inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CollectionModel<GrupoModel> listar(
		@Parameter(description = "Id do usuário", example = "1", required = true)
			Long usuarioId);
	
	@Operation(summary = "Associação de usuário em um grupo", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> associarGrupo(
		@Parameter(description = "Id do usuário", example = "1", required = true)
			Long usuarioId, 
		@Parameter(description = "Id do grupo", example = "1", required = true)
			Long grupoId);
	
	@Operation(summary = "Desassociação de usuário de um grupo", responses = {
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> desassociarGrupo(
		@Parameter(description = "Id do usuário", example = "1", required = true)
			Long usuarioId, 
		@Parameter(description = "Id do grupo", example = "1", required = true)
			Long grupoId);
	
}
