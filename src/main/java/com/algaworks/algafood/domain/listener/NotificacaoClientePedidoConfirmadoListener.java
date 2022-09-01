package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	//@TransactionalEventListener - Envia o e-mail após a confirmação do update no banco de dados,
	//porém se der erro de envio, não será feito rollback no banco de dados
	
	//@TransactionalEventListener(phase - TransactionalPhase.BEFORE_COMMIT) - Envia o e-mail após 
	//a confirmação do update no banco de dados, mas se der erro de envio, será feito rollback no banco de dados
	
	@TransactionalEventListener 
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	}
}
