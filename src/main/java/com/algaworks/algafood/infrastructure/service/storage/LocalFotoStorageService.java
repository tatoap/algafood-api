package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {
	
	private static final String MSG_ARQUIVO_NAO_RECUPERADO = "Não foi possível recuperar o arquivo.";
	private static final String MSG_ARQUIVO_NAO_EXCLUIDO = "Não foi possível excluir o arquivo.";
	private static final String MSG_ARQUIVO_NAO_SALVO = "Não foi possível armazenar o arquivo.";
	
	//@Value("${algafood.storage.local.diretorio-fotos}")
	//private Path diretorioFotos;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException(MSG_ARQUIVO_NAO_SALVO, e);
		}
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		//return diretorioFotos.resolve(Path.of(nomeArquivo));	
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {		
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
					.inputStream(Files.newInputStream(arquivoPath))
					.build();
			
			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException(MSG_ARQUIVO_NAO_RECUPERADO, e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			System.out.println("Passei aqui");
			Path arquivoPath = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException(MSG_ARQUIVO_NAO_EXCLUIDO, e);
		}
	}
}
