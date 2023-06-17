package br.edu.ifrn.portal.dl.services;

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
	
	public Disciplina obterPorId(Long id) throws IllegalArgumentException{
		return disciplinaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
	}
	
	public Page<Disciplina> getDisciplinasPaginadas(Pageable pageable) {
		return disciplinaRepository.findAllAsc(pageable);
	}
	
	public Page<Disciplina> obterDisciplinasPorNomePaginadas(String nome, Pageable pageable){
		return disciplinaRepository.findByNomePagined(nome, pageable);
	}
	/*
	 * public Page<Disciplina> getDisciplinasPaginadas() { return
	 * getDisciplinasPaginadas(0, 10); }
	 */
	/*
	 * public Page<Disciplina> getDisciplinasPaginadas(int page, int size) { if
	 * (page < 0) page = 0; if (size > 10) size = 10; if (size < 5) size = 5;
	 * 
	 * return disciplinaRepository.findAll(PageRequest.of(page, size,
	 * Sort.by("id"))); }
	 */

	/*
	 * public Page<Disciplina> getDisciplinasPaginadas(Pageable pageable) { return
	 * getDisciplinasPaginadas(pageable.getPageNumber(), pageable.getPageSize()); }
	 */
	public String getImage(long id) {
		return disciplinaRepository.obterImagem(id);
	}

	/*---------------DELETE---------------*/

	public void remover(Long id) {
		disciplinaRepository.deleteById(id);
	}
}
