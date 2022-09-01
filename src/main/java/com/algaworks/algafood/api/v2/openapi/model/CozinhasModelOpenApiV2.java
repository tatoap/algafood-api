package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;

import lombok.Data;

//@ApiModel("CozinhasModel")
@Data
public class CozinhasModelOpenApiV2 {
	
	private CozinhasEmbeddedOpenApiV2 _embedded;
	
	private Links _links;
	
	private PageModelOpenApiV2 page;

	//@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedOpenApiV2 {
		
		private List<CozinhaModelV2> cozinhas;
		
	}
	
}
