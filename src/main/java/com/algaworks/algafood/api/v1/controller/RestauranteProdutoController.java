package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId, 
			@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		List<Produto> produtos = null;
		
		if (incluirInativos) {
			produtos = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return produtoModelAssembler.toCollectionModel(produtos)
				.add(algaLinks.linkToProdutos(restauranteId));
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produto = cadastroProduto.buscarOuFalhar(restaurante.getId(), produtoId);
		
		return produtoModelAssembler.toModel(produto);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@RequestBody @Valid ProdutoInput produtoInput, @PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@RequestBody @Valid ProdutoInput produtoInput, 
			@PathVariable Long produtoId, @PathVariable Long restauranteId){	
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produtoAtual = cadastroProduto.buscarOuFalhar(restaurante.getId(), produtoId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		return produtoModelAssembler.toModel(cadastroProduto.salvar(produtoAtual));
	}
}
