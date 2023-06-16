package br.edu.ifrn.portal.dl.utils;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

public class ConversorImagem {
	public static String imagemString;
	
	public static String encodeImagem(byte[] imageByteArray) {
        return Base64.encodeBase64String(imageByteArray);
    }
	
    /*este método realiza a conversão da imagem recebido e a 
      devolve como uma string.
    */		
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
