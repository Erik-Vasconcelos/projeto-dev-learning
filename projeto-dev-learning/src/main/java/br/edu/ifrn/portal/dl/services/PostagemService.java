package br.edu.ifrn.portal.dl.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.repositories.PostagemRepository;

/**
 * Classe responsável por encapsular o objeto de acesso a dados da <strong>entidade 
 * Postagem<strong> e disponibilizar os serviços ofertados pela mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version A0.2
 */

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;

	/*---------------CREATE and UPDATE---------------*/
	
	public Postagem salvar(@Valid Postagem postagem) {
		return postagemRepository.save(postagem);
	}

	/*---------------READ---------------*/
	
	public Optional<Postagem> obterPorId(Long id) throws IllegalArgumentException{
		return postagemRepository.findById(id);
	}
	
	public Page<Postagem> getPostagensPaginadas(Pageable pageable) {
		return postagemRepository.findAllAsc(pageable);
	}
	
	public Page<Postagem> getPostagensPorTituloPaginadas(String titulo, Pageable pageable){
		return postagemRepository.findByTituloPagined(titulo, pageable);
	}

	/*---------------DELETE---------------*/

	public void remover(Long id) {
		postagemRepository.deleteById(id);
	}
	
	/*---------------AUXILIARES---------------*/
	public String getImage(Long id) {
		return postagemRepository.findImagem(id);
	}
	
	public boolean titleExists(String titulo) {
		return postagemRepository.countByTitulo(titulo) > 0 ? true : false;
	}
	
	public boolean nameIsDuplicate(Long id, String name) {
		return postagemRepository.countOccurrenceName(id, name) > 0 ? true : false;
	}
	
}
