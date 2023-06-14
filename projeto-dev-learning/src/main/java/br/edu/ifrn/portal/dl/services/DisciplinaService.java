package br.edu.ifrn.portal.dl.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.repositories.DisciplinaRepository;

/**
 * 
 * @author erikv
 * @since 13/06/2023
 * 
 */

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	public List<Disciplina> getDisciplinas() {
		return disciplinaRepository.findAllAsc();
	}

	public Page<Disciplina> getDisciplinasPaginadas() {
		return getDisciplinasPaginadas(0, 10);
	}

	public Page<Disciplina> getDisciplinasPaginadas(int page, int size) {
		if (page < 0) page = 0;
		if (size > 10) size = 10;
		if (size < 5) size = 5;

		return disciplinaRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
	}

	public Page<Disciplina> getDisciplinasPaginadas(Pageable pageable) {
		return getDisciplinasPaginadas(pageable.getPageNumber(), pageable.getPageSize());
	}

	public Disciplina obterPorId(Long id) {
		return disciplinaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
	}
	
	public String getImage(long id) {
		return disciplinaRepository.obterImagem(id);
	}

	/*
	 * public Page<Disciplina> obterPorParteNome(String nome){ return
	 * disciplinaRepository.findByNameLimited(nome, PageRequest.of(0, 10)); }
	 * 
	 * public Page<Disciplina> obterPorParteNome(String nome, int page, int size){
	 * if(page < 0)page = 0; if(size > 10) size = 10; if(size < 5) size = 5;
	 * 
	 * return disciplinaRepository.findByNameLimited(nome, PageRequest.of(page,
	 * size)); }
	 */

	public Disciplina salvar(@Valid Disciplina disciplina) {
		return disciplinaRepository.save(disciplina);
	}

	public void remover(Long id) {
		disciplinaRepository.deleteById(id);
	}
}
