package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida, pois esta em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired 
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Transactional
	public Restaurante salvar(@RequestBody Restaurante restaurante) {
		restauranteRepository.detach(restaurante);
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush(); // força o sistema a fazer a exclusão no banco de dados (não deixa na fila de processo)
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	@Transactional
	public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		restaurante.desassociarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.desassociarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
	}
	
	@Transactional
	public void ativarMultiplos(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativarMultiplos(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
	
	@Transactional
	public void abertura(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.abrir();
	}
	
	@Transactional
	public void fechamento(Long restauranteId) {
		
	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	
	restauranteAtual.fechar();
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}
