package com.algaworks.algafood.domain.exception;

public class UsuarioSenhaIncorretaException extends NegocioException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_USUARIO_SENHA_INCORRETA = "Senha informada não coincide com a senha do usuário";

	public UsuarioSenhaIncorretaException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioSenhaIncorretaException(Long usuarioId) {
		this(String.format(MSG_USUARIO_SENHA_INCORRETA, usuarioId));
	}
}
