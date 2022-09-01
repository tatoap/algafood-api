package com.algaworks.algafood.core.springdoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
		in = ParameterIn.QUERY, 
		name = "clienteId", 
		description = "Id do cliente para filtro da pesquisa",
		example = "1",
		schema = @Schema(type = "integer")
)
@Parameter(
		in = ParameterIn.QUERY, 
		name = "restauranteId", 
		description = "Id do restaurante para filtro da pesquisa",
		example = "1",
		schema = @Schema(type = "integer")
)
@Parameter(
		in = ParameterIn.QUERY, 
		name = "dataCriacaoInicio", 
		description = "Data/hora de criação inicial para filtro da pesquisa", 
		example = "2019-10-30T00:00:00Z"
)
@Parameter(
		in = ParameterIn.QUERY, 
		name = "dataCriacaoFim", 
		description = "Data/hora de criação final para filtro da pesquisa", 
		example = "2019-10-30T00:00:00Z"
)
public @interface PedidosParameter {

}
