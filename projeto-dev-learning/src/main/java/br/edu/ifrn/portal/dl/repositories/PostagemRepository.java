package br.edu.ifrn.portal.dl.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.utils.InfoDisciplina;
import br.edu.ifrn.portal.dl.utils.InfoPostagens;

/**
 * Interface responsável pela comunicação com o banco de dados referente aos
 * processos da <strong>entidade Postagem<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-29
 * @version 1.0 2023-09-03
 */

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	public List<Postagem> findByTituloContainingIgnoreCase(String titulo);

	public Optional<Postagem> findByTituloIgnoreCase(String titulo);

	@Query("SELECT p FROM Postagem p WHERE lower(p.titulo) LIKE lower(concat('%', :titulo, '%')) ORDER BY p.id ASC")
	public Page<Postagem> findByTituloPagined(@Param("titulo") String titulo, Pageable pageable);

	@Query("SELECT p FROM Postagem p WHERE lower(p.titulo) LIKE lower(concat('%', :titulo, '%')) ORDER BY p.dataPostagem DESC")
	public Page<Postagem> findByTituloPaginedOrderData(@Param("titulo") String titulo, Pageable pageable);

	@Query("SELECT p FROM Postagem p ORDER BY p.id ASC")
	public Page<Postagem> findAllAsc(Pageable pageable);

	@Query("SELECT p FROM Postagem p ORDER BY p.dataPostagem DESC, p.id DESC")
	public Page<Postagem> findAllOrderDataDesc(Pageable pageable);

	@Query("SELECT p FROM Postagem p WHERE p.autor.id = :id ORDER BY p.dataPostagem DESC")
	public Page<Postagem> findByIdAutorOrderByData(@Param("id") Long idAutor, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT * FROM postagens p INNER JOIN postagens_tecnologias pt ON p.id = pt.postagem_id WHERE pt.tecnologias_id = :id ORDER BY p.data_postagem DESC")
	public Page<Postagem> findByTecnologiaId(@Param("id") Long idTecnologia, Pageable pageable);

	@Query("SELECT p FROM Postagem p INNER JOIN Disciplina d ON p.disciplina.id = d.id WHERE d.id = :id ORDER BY p.dataPostagem DESC")
	public Page<Postagem> findByDisciplinaOderByDataDesc(@Param("id") Long idDisciplina, Pageable pageable);

	@Query("SELECT p.imagem FROM Postagem p WHERE p.id = :id")
	public String findImagem(@Param("id") Long id);

	@Query("SELECT count(p.id) FROM Postagem p WHERE p.titulo = :titulo")
	public Long countByTitulo(@Param("titulo") String titulo);

	@Query("SELECT count(p.id) FROM Postagem p WHERE p.titulo = :titulo AND p.id <> :id")
	public Long countOccurrenceName(@Param("id") Long id, @Param("titulo") String titulo);

	@Query(nativeQuery = true)
	public List<InfoPostagens> countPostsByType();

	@Query(nativeQuery = true)
	public List<InfoDisciplina> countPostByDisciplinas();

}
