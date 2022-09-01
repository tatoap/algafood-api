package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.controller.EstatisticasController.EstatisticasModel;
import com.algaworks.algafood.core.springdoc.EstatisticasParameter;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estatísticas")
@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {
	
	@Operation(description = "Estatísticas", hidden = true)
	EstatisticasModel estatisticas();
	
	@Operation(summary = "Consulta as estatísticas de vendas diárias", responses = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendaDiaria.class))),
					@Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))
			})
	})
	@EstatisticasParameter
	List<VendaDiaria> consultarVendasDiarias(
			@Parameter(hidden = true) VendaDiariaFilter filtro, 
			@Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", 
				schema = @Schema(type = "string", defaultValue = "+00:00")) String timeOffset);
	
	@Operation(hidden = true)
	ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, String timeOffset);

}
