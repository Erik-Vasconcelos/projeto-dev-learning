package br.edu.ifrn.portal.dl.utils;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe responsável por realizar a codificação de uma imagem para o formato base64
 * que é utilizado para ser gravado no banco de dados.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-15
 * @version 1.0 2023-09-03
 */

public class ConversorImagem {
	public static String imagemString;
	
	public static String encodeImagem(byte[] imageByteArray) {
        return Base64.encodeBase64String(imageByteArray);
    }
		
	public static String gravarImagemBase64(MultipartFile file){
		try{
			imagemString = encodeImagem(file.getBytes());
		}catch (IOException e) {
			e.printStackTrace();
		}
		return imagemString;
	}
	
	public static String getImagemEncoded(MultipartFile file) {
		gravarImagemBase64(file);
		imagemString = "data:image/png;base64," + imagemString;
		return imagemString;
	}
}
