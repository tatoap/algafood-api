package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		String nomeArquivoExistente = null;
		
		if (fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(nomeNovoArquivo)
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorage.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
		try {
			Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
			FotoProduto fotoProduto = buscarOuFalhar(restaurante.getId(), produtoId);
			produtoRepository.delete(fotoProduto);
			produtoRepository.flush();
			
			fotoStorage.remover(fotoProduto.getNomeArquivo());
		} catch (EmptyResultDataAccessException e) {
			throw new FotoProdutoNaoEncontradoException(restauranteId, produtoId);
		}
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
	}
}
