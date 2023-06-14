package br.edu.ifrn.portal.dl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Disciplina;

/**
 * 
 * @author erikv
 * @since 08/06/2023
 * 
 */

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{

	
	@Query("SELECT d FROM Disciplina d ORDER BY d.id ASC") 
	 public List<Disciplina> findAllAsc();
	 
	@Query("SELECT d.imagem FROM Disciplina d WHERE d.id = :id") 
	public String obterImagem(@Param("id") long id);
	 
	 
	 /* 
	 * @Query("SELECT t FROM Tecnologia t WHERE lower(t.nome) LIKE  lower(concat('%', :nome, '%')) ORDER BY t.id ASC"
	 * ) public Page<Tecnologia> findByNameLimited(@Param("nome") String nome,
	 * Pageable pageable);
	 */	
}
