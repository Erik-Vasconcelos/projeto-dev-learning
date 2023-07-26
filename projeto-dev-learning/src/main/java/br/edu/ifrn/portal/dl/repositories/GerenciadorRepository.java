package br.edu.ifrn.portal.dl.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Gerenciador;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos processos 
 * da <strong>entidade Gerenciador<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-21
 * @version A0.1
 */

@Repository
public interface GerenciadorRepository extends JpaRepository<Gerenciador, Long>{
	
	public Optional<Gerenciador> findUsuarioByEmail(String email);
	
}
