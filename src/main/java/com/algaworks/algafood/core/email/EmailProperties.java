package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

//Atribuimos FAKE como padrão
//Isso evita o problema de enviar e-mails de verdade caso você esqueça
//de definir a propriedade

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	
	private Sandbox sandbox = new Sandbox();
	
	private Implementacao impl = Implementacao.FAKE;
	
	public enum Implementacao {
		
		SMTP, FAKE, SANDBOX
	}

	@NotNull
	private String remetente;
	
	@Getter
	@Setter
	public class Sandbox {
		
		public String destinatario;
		
	}
	
}
