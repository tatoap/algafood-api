package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe um pedido com o código %s";
	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format(MSG_PEDIDO_NAO_ENCONTRADO, codigoPedido));
	}
	
}
