package br.edu.ifrn.portal.dl.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public List<Disciplina> findByNomeContainingIgnoreCase(String nome);
	
	@Query("SELECT d FROM Disciplina d WHERE lower(d.nome) LIKE  lower(concat('%', :nome, '%')) ORDER BY d.id ASC")
	public Page<Disciplina> findByNomePagined(@Param("nome") String nome, Pageable pageable);
			
	@Query("SELECT d FROM Disciplina d ORDER BY d.id ASC") 
	 public Page<Disciplina> findAllAsc(Pageable pageable);
	
	@Query("SELECT d.imagem FROM Disciplina d WHERE d.id = :id") 
	public String obterImagem(@Param("id") long id);
	 
}
