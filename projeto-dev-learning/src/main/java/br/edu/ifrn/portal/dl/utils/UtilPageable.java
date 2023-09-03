package br.edu.ifrn.portal.dl.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Classe responsável por gerenciar a regra de <strong>validação do Pageable<strong> passado para os controllers, 
 * afim de verificar se a quantidade de registros da requisição não é maior do que a quantidade
 *  definida no controller. 
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-19
 * @version 1.0 2023-09-03
 */
public class UtilPageable {

	public static Pageable verifySizePageable(final int REGISTROS_POR_PAGINA, Pageable pageable) {
		if (pageable.getPageSize() > REGISTROS_POR_PAGINA) {
			return PageRequest.of(pageable.getPageNumber(), REGISTROS_POR_PAGINA);
		} else {
			return pageable;
		}
	}
	
}
