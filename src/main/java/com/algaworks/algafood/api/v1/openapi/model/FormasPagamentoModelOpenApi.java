package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Link;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import lombok.Data;

@Data
public class FormasPagamentoModelOpenApi {
	
	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	
	private Link _links;
	
	@Data
	public class FormasPagamentoEmbeddedModelOpenApi {
		
		private List<FormaPagamentoModel> formasPagamento;
		
	}

}
