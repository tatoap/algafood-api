package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Link;

import com.algaworks.algafood.api.v1.model.EstadoModel;

import lombok.Data;

@Data
public class EstadosModelOpenApi {
	
	private EstadosEmbeddedModelOpenApi _embedded;
	
	private Link _links;
	
	@Data
	public class EstadosEmbeddedModelOpenApi {
		
		private List<EstadoModel> estados;
		
	}
}
