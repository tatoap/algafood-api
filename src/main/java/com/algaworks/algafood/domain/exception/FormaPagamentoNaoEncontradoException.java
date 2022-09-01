package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;
	private static final String MSG_FORMAPAGAMENTO_NAO_ENCONTRADO = "Não existe um cadastro de forma de pagamento com o código %d";

	public FormaPagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
		this(String.format(MSG_FORMAPAGAMENTO_NAO_ENCONTRADO, formaPagamentoId));
	}

}
