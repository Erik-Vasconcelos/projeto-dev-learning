package br.edu.ifrn.portal.dl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.security.Role;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos processos 
 * da <strong>entidade Role<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-08-20
 * @version 0.1
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Query("SELECT r FROM Role r WHERE r.nivelPermissao <= :codigoGerenciador")
	public List<Role> getRolesPorTipoGerenciador(int codigoGerenciador);

}
