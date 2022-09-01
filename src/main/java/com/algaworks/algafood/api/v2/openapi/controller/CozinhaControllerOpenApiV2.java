package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;

//@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

	//@ApiOperation("Lista as cozinhas com paginação")
	PagedModel<CozinhaModelV2> listar(Pageable pageable);
	
	//@ApiOperation("Busca uma cozinha por ID")
	//@ApiResponses({ 
	//	@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
	//	@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	//})
	CozinhaModelV2 buscar(
			//@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
				Long idCozinha);
	
	//@ApiOperation("Cadastra uma cozinha")
	//@ApiResponses({
	//	@ApiResponse(code = 201, message = "Cozinha cadastrada")
	//})
	CozinhaModelV2 adicionar(
			//@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) 
				CozinhaInputV2 cozinhaInput);
	
	//@ApiOperation("Atualiza uma cozinha por ID")
	//@ApiResponses({ 
	//	@ApiResponse(code = 200, message = "Cozinha atualizada"),
	//	@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	//})
	CozinhaModelV2 atualizar(
			//@ApiParam(value = "ID de uma cozinha", example = "1", required = true) 
				Long cozinhaId, 
			//@ApiParam(name = "corpo", value = "Representação de uma cozinha atualizada", required = true) 
				CozinhaInputV2 cozinhaInput);
	
	//@ApiOperation("Exclui uma cozinha por ID")
	//@ApiResponses({ 
	//	@ApiResponse(code = 204, message = "Cozinha excluida"),
	//	@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	//})
	void remover(
			//@ApiParam(value = "ID de uma cozinha", example = "1", required = true) 
				Long idCozinha);
	
}