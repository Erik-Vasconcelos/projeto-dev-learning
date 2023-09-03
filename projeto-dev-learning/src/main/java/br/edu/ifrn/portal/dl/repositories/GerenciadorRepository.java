package br.edu.ifrn.portal.dl.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Gerenciador;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos processos 
 * da <strong>entidade Gerenciador<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-07-21
 * @version 1.0 2023-09-03
 */

@Repository
public interface GerenciadorRepository extends JpaRepository<Gerenciador, Long>{
	
	public Optional<Gerenciador> findUsuarioByEmail(String email);
	
	@Query("SELECT g FROM Gerenciador g WHERE g.tipoGerenciador <> :codigo ORDER BY g.id ASC") 
	public Page<Gerenciador> findAllAsc(@Param(value = "codigo") int codigoGerenciadorMaster, Pageable pageable);
	
	@Query("SELECT g FROM Gerenciador g WHERE g.tipoGerenciador <> :codigo and lower(g.nome) LIKE lower(concat('%', :nome, '%')) ORDER BY g.id ASC")
	public Page<Gerenciador> findByNomePagined(@Param(value = "codigo") int codigoGerenciadorMaster, @Param("nome") String nome, Pageable pageable);
	
	@Query("SELECT g.imagem FROM Gerenciador g WHERE g.id = :id") 
	public String findImagem(@Param("id") Long id);
	
	@Query("SELECT count(g.id) FROM Gerenciador g WHERE g.nome = :nome") 
	public Long countByName(@Param("nome") String nome);
	
	@Query("SELECT count(g.id) FROM Gerenciador g WHERE g.nome = :nome AND g.id <> :id") 
	public Long countOccurrenceName(@Param("id") Long id, @Param("nome") String nome);
	
}
