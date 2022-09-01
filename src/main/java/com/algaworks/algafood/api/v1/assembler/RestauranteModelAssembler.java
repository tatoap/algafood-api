package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	
	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteModel);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
			
			restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		}
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteProdutos(restaurante.getId(), "produtos"));
		}
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		
		if (algaSecurity.podeGerenciarFuncionamentoRestaurante(restaurante.getId())) {
			if (restaurante.aberturaPermitida()) {
				restauranteModel.add(algaLinks.linkToAberturaRestaurante(restaurante.getId(), "abrir"));
			}
			
			if (restaurante.fechamentoPermitido()) {
				restauranteModel.add(algaLinks.linkToFechamentoRestaurante(restaurante.getId(), "fechar"));
			}
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToAtivacaoRestaurante(restaurante.getId(), "ativar"));
			}
			
			if (restaurante.inativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToInativacaoRestaurante(restaurante.getId(), "inativar"));
			}
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade()
					.add(algaLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
			}
		}
		
		return restauranteModel;
	}
	
	@Override 
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteModel> toCollectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			toCollectionModel.add(algaLinks.linkToRestaurantes());
		}
		
		return toCollectionModel;
	}
}
