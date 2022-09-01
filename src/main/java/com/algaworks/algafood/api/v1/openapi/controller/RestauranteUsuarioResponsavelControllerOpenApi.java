package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.UsuarioModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@Operation(summary = "Lista os usuários responsáveis associados a restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CollectionModel<UsuarioModel> listar(
		@Parameter(description = "Id do restaurante", example = "1", required = true) 
			Long restauranteId);
	
	@Operation(summary = "Associa um usuário responsável para um restaurante", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> adicionarResponsavel(
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId, 
		@Parameter(description = "Id do responsável", example = "1", required = true)
			Long usuarioId);
	
	@Operation(summary = "Desassocia um usuário responsável de um restaurante", responses = {
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> desassociarResponsavel(
		@Parameter(description = "Id do restaurante", example = "1", required = true)
			Long restauranteId, 
		@Parameter(description = "Id do responsável", example = "1", required = true)
			Long usuarioId);

}
