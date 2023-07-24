package br.edu.ifrn.portal.dl.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Gerenciador;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos processos 
 * da <strong>entidade Disciplina<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version A0.2
 */

@Repository
public interface GerenciadorRepository extends JpaRepository<Gerenciador, Long>{
	
	public Optional<Gerenciador> findUsuarioByLogin(String login);
	
	/*public List<Disciplina> findByNomeContainingIgnoreCase(String nome);
	
	@Query("SELECT d FROM Disciplina d WHERE lower(d.nome) LIKE  lower(concat('%', :nome, '%')) ORDER BY d.id ASC")
	public Page<Disciplina> findByNomePagined(@Param("nome") String nome, Pageable pageable);
			
	@Query("SELECT d FROM Disciplina d ORDER BY d.id ASC") 
	 public Page<Disciplina> findAllAsc(Pageable pageable);
	
	@Query("SELECT d.imagem FROM Disciplina d WHERE d.id = :id") 
	public String findImagem(@Param("id") Long id);
	
	@Query("SELECT count(d.id) FROM Disciplina d WHERE d.nome = :nome") 
	public Long countByName(@Param("nome") String nome);
	
	@Query("SELECT count(d.id) FROM Disciplina d WHERE d.nome = :nome AND d.id <> :id") 
	public Long countOccurrenceName(@Param("id") Long id, @Param("nome") String nome);
	
	@Query("SELECT count(p.id) FROM Disciplina d INNER JOIN Postagem p on d.id = p.disciplina.id WHERE d.id = :id") 
	public Long countRelatedPosts(@Param("id") Long id);
	 */
}
