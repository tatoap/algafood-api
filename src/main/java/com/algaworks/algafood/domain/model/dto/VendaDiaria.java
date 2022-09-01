package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estatisticas")
@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria extends RepresentationModel<VendaDiaria> {
	
	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;

}
