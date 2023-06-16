package br.edu.ifrn.portal.dl.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Disciplina;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos processos 
 * da <strong>entidade Disciplina<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version A0.2
 */

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
	
	@Query("SELECT d FROM Disciplina d ORDER BY d.id ASC") 
	 public List<Disciplina> findAllAsc();
	 
	@Query("SELECT d.imagem FROM Disciplina d WHERE d.id = :id") 
	public String obterImagem(@Param("id") long id);
	 
}
