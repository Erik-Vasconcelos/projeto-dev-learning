package br.edu.ifrn.portal.dl.repositories;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Tecnologia;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos processos 
 * da <strong>entidade Tecnologia<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-08
 * @version A0.2 2023-06-22
 */

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long>{

	@Query("SELECT t FROM Tecnologia t ORDER BY t.id ASC") 
	 public Page<Tecnologia> findAllAsc(Pageable pageable);
	
	@Query("SELECT t FROM Tecnologia t WHERE lower(t.nome) LIKE  lower(concat('%', :nome, '%')) ORDER BY t.id ASC")
	public Page<Tecnologia> findByNomePagined(@Param("nome") String nome, Pageable pageable);
	
	@Query("SELECT count(d.id) FROM Tecnologia d WHERE d.nome = :nome") 
	public Long countByName(@Param("nome") String nome);
	
	@Query("SELECT count(t.id) FROM Tecnologia t WHERE t.nome = :nome AND t.id <> :id") 
	public Long countOccurrenceName(@Param("id") Long id, @Param("nome") String nome);
	
	//@Query("SELECT count(pt.postagem_id) FROM postagens_tecnologias pt WHERE pt.tecnologias_id = :id)") 
	public Long countRelatedPosts(Long id);
	
}
