package br.edu.ifrn.portal.dl.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.repositories.DisciplinaRepository;

/**
 * Classe responsável por encapsular o objeto de acesso a dados da <strong>entidade 
 * Disciplina<strong> e disponibilizar os serviços ofertados pela mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version A0.2
 */

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	/*---------------CREATE and UPDATE---------------*/
	
	public Disciplina salvar(@Valid Disciplina disciplina) {
		return disciplinaRepository.save(disciplina);
	}

	/*---------------READ---------------*/
	
	public Optional<Disciplina> obterPorId(Long id) throws IllegalArgumentException{
		return disciplinaRepository.findById(id);
	}
	
	public Page<Disciplina> getDisciplinasPaginadas(Pageable pageable) {
		return disciplinaRepository.findAllAsc(pageable);
	}
	
	public Page<Disciplina> getDisciplinasPorNomePaginadas(String nome, Pageable pageable){
		return disciplinaRepository.findByNomePagined(nome, pageable);
	}

	/*---------------DELETE---------------*/

	public void remover(Long id) {
		disciplinaRepository.deleteById(id);
	}
	
	/*---------------AUXILIARES---------------*/
	public String getImage(Long id) {
		return disciplinaRepository.findImagem(id);
	}
	
	public boolean nameExists(String name) {
		return disciplinaRepository.countByName(name) > 0 ? true : false;
	}
	
	public boolean nameIsDuplicate(Long id, String name) {
		return disciplinaRepository.countOccurrenceName(id, name) > 0 ? true : false;
	}
	
}
