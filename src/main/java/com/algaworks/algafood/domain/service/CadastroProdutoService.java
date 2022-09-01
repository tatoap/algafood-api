package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;

import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto salvar(@RequestBody Produto produto) {
		produtoRepository.detach(produto);
		
		return produtoRepository.save(produto);		
	}
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}
}
