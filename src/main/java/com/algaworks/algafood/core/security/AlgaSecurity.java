package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public boolean isAutenticado() {
		return getAuthentication().isAuthenticated();
	}
	
	public boolean temEscopoEscrita() {
		return hasAuthority("SCOPE_WRITE");
	}
	
	public boolean temEscopoLeitura() {
		return hasAuthority("SCOPE_READ");
	}
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
		if (restauranteId == null) {
			return false;
		}
		
		return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}
	
	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
		if (codigoPedido == null) {
			return false;
		}
		
		return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	}
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null
				&& getUsuarioId().equals(usuarioId);
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
				|| gerenciaRestauranteDoPedido(codigoPedido));
	}
	
	public boolean podeConsultarCozinhas() {
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeConsultarCidades() {
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeConsultarEstados() {
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeConsultarRestaurantes() {
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeGerenciarCadastroRestaurantes() {
		return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}
	
	public boolean podeGerenciarFuncionamentoRestaurante(Long restauranteId) {
		return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
				|| gerenciaRestaurante(restauranteId));
	}
	
	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
		return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
				|| gerenciaRestaurante(restauranteId) || usuarioAutenticadoIgual(clienteId));
	}
	
	public boolean podePesquisarPedidos() {
		return temEscopoLeitura() && isAutenticado(); 
	}
	
	public boolean podeConsultarFormasPagamento() {
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeConsultarUsuariosGruposPermissoes() {
		return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeEditarUsuariosGruposPermissoes() {
		return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeConsultarEstatisticas() {
		return temEscopoEscrita() && hasAuthority("GERAR_RELATORIOS");
	}
}
