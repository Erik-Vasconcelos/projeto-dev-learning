package br.edu.ifrn.portal.dl.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.dtos.GerenciadorDTO;
import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.repositories.GerenciadorRepository;

/**
 * Classe responsável por encapsular o objeto de acesso a dados da <strong>entidade 
 * Gerenciador <strong> e disponibilizar os serviços ofertados pela mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-21
 * @version A0.1
 */

@Service
@Transactional
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
	
	public Optional<Gerenciador> obterPorId(Long id) throws IllegalArgumentException{
		return gerenciadorRepository.findById(id);
	}
	
}
