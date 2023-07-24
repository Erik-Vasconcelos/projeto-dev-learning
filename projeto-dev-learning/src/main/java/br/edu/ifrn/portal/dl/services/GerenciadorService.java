package br.edu.ifrn.portal.dl.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.repositories.GerenciadorRepository;

@Service
@Transactional
public class GerenciadorService implements UserDetailsService {

	@Autowired
	private GerenciadorRepository gerenciadorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Gerenciador> optional = gerenciadorRepository.findUsuarioByLogin(username);
		
		if (optional.isPresent()) {
			Gerenciador gerenciador = optional.get();
			
			return new User(gerenciador.getLogin(), gerenciador.getPassword(), gerenciador.isEnabled(),true, true, true, gerenciador.getAuthorities());
		}else {
			throw new UsernameNotFoundException("Usuário não foi encontrado!");
		}
	}
	
}
