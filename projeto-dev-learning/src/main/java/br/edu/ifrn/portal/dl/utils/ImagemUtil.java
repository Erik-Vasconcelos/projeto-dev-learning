package br.edu.ifrn.portal.dl.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Classe responsável por buscar imagens dentro do projeto e converte para base64
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-15
 * @version 1.0 2024-01-15
 */

public class ImagemUtil {
	
	public static String obterImagemBase64(String nomeDoArquivo) {
        try {
        	 // Carregar o arquivo usando o Resource
            Resource resource = new ClassPathResource("static/imagens/padrao/" + nomeDoArquivo);
            
            // Lê os bytes do arquivo
            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
            
            // Converte os bytes para Base64
            String base64 = Base64.getEncoder().encodeToString(bytes);

            // Formato da imagem base64: data:image/<extensao>;base64,<imagem>
            String formatoImagem = obterFormatoImagem(nomeDoArquivo);
            String imagemBase64 = "data:image/" + formatoImagem + ";base64," + base64;


            return imagemBase64;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String obterFormatoImagem(String nomeDoArquivo) {
        int pontoIndex = nomeDoArquivo.lastIndexOf(".");
        if (pontoIndex != -1) {
            return nomeDoArquivo.substring(pontoIndex + 1);
        }
        return "png";
    }

}
