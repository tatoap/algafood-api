package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;
	private static final String MSG_PERMISSAO_NAO_ENCONTRADA = "Não existe um cadastro de permissao com o código %d";

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoEncontradaException(Long permissaoId) {
		this(String.format(MSG_PERMISSAO_NAO_ENCONTRADA, permissaoId));
	}

}
