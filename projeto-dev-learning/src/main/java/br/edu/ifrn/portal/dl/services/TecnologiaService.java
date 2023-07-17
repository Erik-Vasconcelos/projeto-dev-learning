package br.edu.ifrn.portal.dl.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.repositories.TecnologiaRepository;

/**
 * Classe responsável por encapsular o objeto de acesso a dados da <strong>entidade 
 * Tecnologia<strong> e disponibilizar os serviços ofertados pela mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-22
 * @version A0.2 2023-06-13
 */

@Service
public class TecnologiaService {

	@Autowired
	private TecnologiaRepository tecnologiaRepository;

	/*---------------CREATE and UPDATE---------------*/
	
	public Tecnologia salvar(@Valid Tecnologia tecnologia) {
		return tecnologiaRepository.save(tecnologia);
	}

	/*---------------READ---------------*/
	
	public List<Tecnologia> getListTecnologias() {
		return tecnologiaRepository.findAll();
	}
	
	public Optional<Tecnologia> obterPorId(Long id) throws IllegalArgumentException{
		return tecnologiaRepository.findById(id);
	}
	
	public Page<Tecnologia> getTecnologiasPaginadas(Pageable pageable) {
		return tecnologiaRepository.findAllAsc(pageable);
	}
	
	public Page<Tecnologia> getTecnologiasPorNomePaginadas(String nome, Pageable pageable){
		return tecnologiaRepository.findByNomePagined(nome, pageable);
	}

	/*---------------DELETE---------------*/

	public void remover(Long id) {
		tecnologiaRepository.deleteById(id);
	}
	
	/*---------------AUXILIARES---------------*/
	
	public boolean nameExists(String name) {
		return tecnologiaRepository.countByName(name) > 0 ? true : false;
	}
	
	public boolean nameIsDuplicate(Long id, String name) {
		return tecnologiaRepository.countOccurrenceName(id, name) > 0 ? true : false;
	}
	
	public Long getQuantityRelatedPosts(Long id) {
		return tecnologiaRepository.countRelatedPosts(id);
	}

}
