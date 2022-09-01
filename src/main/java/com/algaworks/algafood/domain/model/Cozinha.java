package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {
	
	@Id
	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Especifica que o campo id é auto-incrementado, seguindo as regras do banco de dados(strategy)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();
}
