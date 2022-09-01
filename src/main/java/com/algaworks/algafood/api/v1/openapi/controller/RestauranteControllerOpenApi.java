package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

	@Operation(summary = "Lista os restaurantes", parameters = {
			@Parameter(name = "projecao",
					description = "Nome da projeção",
					example = "apenas-nome",
					in = ParameterIn.QUERY,
					required = false)
	})
	CollectionModel<RestauranteBasicoModel> listar();
		
	@Operation(description = "Lista os restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();
	
	@Operation(summary = "Busca um restaurante por Id", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Id do restaurante inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))		
	})
	RestauranteModel buscar(
			@Parameter(description = "Id de um restaurante", example = "1", required = true) 
				Long restauranteId);
	
	@Operation(summary = "Cadastra um restaurante", responses = {
			@ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
	})
	RestauranteModel adicionar(
			@Parameter(description = "Representação de um novo restaurante", required = true)
				RestauranteInput restauranteInput);
	
	@Operation(summary = "Atualiza um restaurante por id", responses = {
			@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	RestauranteModel atualizar(
			@Parameter(description = "Id de um restaurante", example = "1", required = true) 
				Long restauranteId, 
			@Parameter(description = "Representação de um restaurante com os novos dados", example = "1", required = true) 
				RestauranteInput restauranteInput);
	
	@Operation(summary = "Exclui um restaurante por Id", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante excluido"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(
			@Parameter(description = "Id de um restaurante", example = "1", required = true)
				Long restauranteId);
	
	@Operation(summary = "Atualiza parcialmente um restaurante por id", responses = {
			@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	RestauranteModel atualizarParcial(
			@Parameter(description = "Id de um restaurante", example = "1", required = true) 
				Long restauranteId, 
			@Parameter(description = "Representação de um restaurante com os novos dados", example = "1", required = true) 
				Map<String, Object> campos, 
				HttpServletRequest request);
	
	@Operation(summary = "Ativa um restaurante por id", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> ativar(
			@Parameter(description = "Id de um restaurante", example = "1", required = true)
				Long restauranteId);
	
	@Operation(summary = "Inativa um restaurante por id", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> inativar(
			@Parameter(description = "Id de um restaurante", example = "1", required = true)
				Long restauranteId);
	
	@Operation(summary = "Ativa múltiplos restaurantes", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
	})
	void ativar(
			@Parameter(description = "Ids dos restaurantes", required = true)
				List<Long> restauranteIds);
	
	@Operation(summary = "Inativa múltiplos restaurantes", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
	})
	void inativar(
			@Parameter(description = "Ids dos restaurantes", required = true)
				List<Long> restauranteIds);
	
	@Operation(summary = "Abre um restaurante por id", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> abertura(
			@Parameter(description = "Id de um restaurante", example = "1", required = true)
				Long restauranteId);
	
	@Operation(summary = "Fecha um restaurante por id", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> fechamento(
			@Parameter(description = "Id de um restaurante", example = "1", required = true)
				Long restauranteId);

}
