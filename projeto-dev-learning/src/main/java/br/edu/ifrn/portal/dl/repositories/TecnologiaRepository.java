package br.edu.ifrn.portal.dl.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Tecnologia;

/**
 * 
 * @author erikv
 * @since 08/06/2023
 * 
 */

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long>{

	@Query("SELECT t FROM Tecnologia t ORDER BY t.id ASC")
	public List<Tecnologia> findAllAsc();
	
	@Query("SELECT t FROM Tecnologia t WHERE lower(t.nome) LIKE  lower(concat('%', :nome, '%')) ORDER BY t.id ASC")
	public Page<Tecnologia> findByNameLimited(@Param("nome") String nome, Pageable pageable);
	
}
