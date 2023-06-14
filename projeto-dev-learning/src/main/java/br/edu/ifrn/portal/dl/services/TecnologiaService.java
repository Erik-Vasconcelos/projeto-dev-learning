package br.edu.ifrn.portal.dl.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.repositories.TecnologiaRepository;

/**
 * 
 * @author erikv
 * @since 08/06/2023
 * 
 */

@Service
public class TecnologiaService {

	@Autowired
	private TecnologiaRepository tecnologiaRepository;


	public List<Tecnologia> getTecnologias() {
		return tecnologiaRepository.findAllAsc();
	}
	
	
	public Page<Tecnologia> getTecnologiasPaginadas() {
		return getTecnologiasPaginadas(0, 10);
	}
	
	public Page<Tecnologia> getTecnologiasPaginadas(int page, int size) {
		if(page < 0)page = 0;
		if(size > 10) size = 10;
		if(size < 5) size = 5;
		
		return tecnologiaRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
	}
	
	public Page<Tecnologia> getTecnologiasPaginadas(Pageable pageable) {
		return getTecnologiasPaginadas(pageable.getPageNumber(), pageable.getPageSize());
	}
	
	public Tecnologia obterPorId(Long id) {
		return tecnologiaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tecnologia não encontrada"));
	}
	
	public Page<Tecnologia> obterPorParteNome(String nome){
		return tecnologiaRepository.findByNameLimited(nome, PageRequest.of(0, 10));
	}
	
	public Page<Tecnologia> obterPorParteNome(String nome, int page, int size){
		if(page < 0)page = 0;
		if(size > 10) size = 10;
		if(size < 5) size = 5;
		
		return tecnologiaRepository.findByNameLimited(nome, PageRequest.of(page, size));
	}

	public Tecnologia salvar(@Valid Tecnologia tecnologia) {
		
		return tecnologiaRepository.save(tecnologia);
	}
	
	public void remover(Long id) {
		tecnologiaRepository.deleteById(id);
	}
}
