package com.algaworks.algafood.domain.exception;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_ITEM_PEDIDO_NAO_ENCONTRADO = "Não existe um cadastro de item com o código %d";

	public ItemPedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ItemPedidoNaoEncontradoException(Long itemPedidoId) {
		this(String.format(MSG_ITEM_PEDIDO_NAO_ENCONTRADO, itemPedidoId));
	}
	
}