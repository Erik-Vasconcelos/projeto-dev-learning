package br.edu.ifrn.portal.dl.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.dtos.GerenciadorDTO;
import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.repositories.GerenciadorRepository;

/**
 * Classe responsável por encapsular o objeto de acesso a dados da <strong>entidade 
 * Gerenciador <strong> e disponibilizar os serviços ofertados pela mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-21
 * @version 1.0 2023-09-03
 */

@Service
public class GerenciadorService implements UserDetailsService {

	@Autowired
	private GerenciadorRepository gerenciadorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Gerenciador> optional = gerenciadorRepository.findUsuarioByEmail(username);
		
		if (optional.isPresent()) {
			GerenciadorDTO gerenciadorDTO = new GerenciadorDTO();
			gerenciadorDTO.fromGerenciadorDTO(optional.get());
			
			return gerenciadorDTO;
		}else {
			throw new UsernameNotFoundException("Usuário não foi encontrado!");
		}
	}
	

	/*---------------CREATE and UPDATE---------------*/
	
	public Gerenciador salvar(Gerenciador gerenciador) {
		return gerenciadorRepository.save(gerenciador);
	}
	
	public Page<Gerenciador> getGerenciadoresPaginados(Pageable pageable) {
		return gerenciadorRepository.findAllAsc(TipoGerencidor.ADMIN_MASTER.getCodigo(), pageable);
	}
	
	public Page<Gerenciador> getGerenciadoresPorNomePaginados(String nome, Pageable pageable){
		return gerenciadorRepository.findByNomePagined(TipoGerencidor.ADMIN_MASTER.getCodigo(), nome, pageable);
	}
	
	public Optional<Gerenciador> obterPorId(Long id) throws IllegalArgumentException{
		return gerenciadorRepository.findById(id);
	}
	
	/*---------------AUXILIARES---------------*/
	
	public String getImage(Long id) {
		return gerenciadorRepository.findImagem(id);
	}
	
	public boolean nameExists(String name) {
		return gerenciadorRepository.countByName(name) > 0 ? true : false;
	}
	
	public boolean nameIsDuplicate(Long id, String name) {
		return gerenciadorRepository.countOccurrenceName(id, name) > 0 ? true : false;
	}
	
}
