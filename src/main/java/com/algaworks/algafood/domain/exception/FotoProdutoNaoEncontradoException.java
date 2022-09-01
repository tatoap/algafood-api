package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_FOTOPRODUTO_NAO_ENCONTRADO = "Não existe um cadastro de foto do produto com o código %d "
			+ "para o restaurante de código %d";

	public FotoProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		this(String.format(MSG_FOTOPRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));
	}
}
