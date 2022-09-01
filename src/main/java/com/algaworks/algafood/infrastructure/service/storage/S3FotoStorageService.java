package com.algaworks.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {
	
	private static final String MSG_ERRO_SALVAR_ARQUIVO_AMAZON_S3 = "Não foi possível enviar o arquivo para Amazon S3.";

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		return FotoRecuperada.builder().url(url.toString()).build();
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException(MSG_ERRO_SALVAR_ARQUIVO_AMAZON_S3, e);
		}
	}
	
	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		
	}

}
