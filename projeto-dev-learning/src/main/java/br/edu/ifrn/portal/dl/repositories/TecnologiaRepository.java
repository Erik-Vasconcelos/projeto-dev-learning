package br.edu.ifrn.portal.dl.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.utils.PostsTecnologia;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos
 * processos da <strong>entidade Tecnologia<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-08
 * @version A0.2 2023-06-22
 */

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {

	@Query("SELECT t FROM Tecnologia t ORDER BY t.id ASC")
	public Page<Tecnologia> findAllAsc(Pageable pageable);

	@Query("SELECT t FROM Tecnologia t WHERE lower(t.nome) LIKE  lower(concat('%', :nome, '%')) ORDER BY t.id ASC")
	public Page<Tecnologia> findByNomePagined(@Param("nome") String nome, Pageable pageable);

	public Optional<Tecnologia> findByNomeIgnoreCase(String nome);

	@Query("SELECT count(d.id) FROM Tecnologia d WHERE d.nome = :nome")
	public Long countByName(@Param("nome") String nome);

	@Query("SELECT count(t.id) FROM Tecnologia t WHERE t.nome = :nome AND t.id <> :id")
	public Long countOccurrenceName(@Param("id") Long id, @Param("nome") String nome);

	public Long countRelatedPosts(Long id);

	@Query(nativeQuery = true)
	public List<PostsTecnologia> getNumbersPostsByTecnology();

	@Query(nativeQuery = true, value = "SELECT t.* FROM tecnologias t LEFT JOIN postagens_tecnologias pt "
			+ "ON t.id = pt.tecnologias_id GROUP BY t.id ORDER BY count(pt.postagem_id) DESC, t.nome LIMIT 10")
	public List<Tecnologia> getPricipaisTecnologias();

}
