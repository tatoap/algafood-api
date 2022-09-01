package com.algaworks.algafood.api.v1.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;;

//@CrossOrigin(maxAge = 10)
@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@Autowired
	private SmartValidator validator;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<RestauranteBasicoModel> listar(){
		return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes(){
		return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return restauranteModelAssembler.toModel(restaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
			
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId) {
		cadastroRestaurante.excluir(restauranteId);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput){
		try {			
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PatchMapping("/{restauranteId}")
	public RestauranteModel atualizarParcial(@PathVariable Long restauranteId, 
			@RequestBody Map<String, Object> campos, HttpServletRequest request){
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
						
			merge(campos, restauranteAtual, request);
			
			validate(restauranteAtual, "restaurante");
			
			return atualizar(restauranteId, restauranteInputDisassembler.toModelObject(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName); 
		validator.validate(restaurante, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true); // deixa os campos privados da classe origem acessiveis
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativarMultiplos(restauranteIds);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativarMultiplos(restauranteIds);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abertura(@PathVariable Long restauranteId) {
		cadastroRestaurante.abertura(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento	
	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechamento(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechamento(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
}
