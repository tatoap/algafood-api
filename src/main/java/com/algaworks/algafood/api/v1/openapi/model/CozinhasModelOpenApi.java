package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.CozinhaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhasModelOpenApi {
	
	private CozinhasEmbeddedModelOpenApi _embedded;
	
	private Links _links;
	
	private PageModelOpenApi page;
	
	@Getter
	@Setter
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaModel> cozinhas;
	
	}
	
}
