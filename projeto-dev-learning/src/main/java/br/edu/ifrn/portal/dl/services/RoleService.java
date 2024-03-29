package br.edu.ifrn.portal.dl.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrn.portal.dl.models.Role;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.repositories.RoleRepository;
import lombok.NoArgsConstructor;

/**
 * Classe responsável por encapsular o objeto de acesso a dados da <strong>entidade 
 * Role<strong> e disponibilizar os serviços ofertados pela mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-20
 * @version 1.0 2023-09-03
 */

@NoArgsConstructor
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role salvar(@Valid Role role) {
		return roleRepository.save(role);
	}
	
	/*---------------READ---------------*/
	public List<Role> obterRolesPorTipoGerenciador(TipoGerencidor tipo){
		return roleRepository.getRolesPorTipoGerenciador(tipo.getCodigo());
	}
	
	
	
}
