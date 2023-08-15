package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Classe modelo da <strong>entidade Tecnologia<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-08
 * @version A0.2 2023-06-22
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "tecnologias")
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = "Tecnologia.countRelatedPosts", 
				query = "SELECT count(pt.postagem_id) FROM postagens_tecnologias pt WHERE pt.tecnologias_id = ?1"),
		@NamedNativeQuery(name = "Tecnologia.getNumbersPostsByTecnology", 
				query = "SELECT t.*, count(pt.postagem_id) AS quantidade FROM tecnologias t INNER JOIN postagens_tecnologias pt "
						+ "ON t.id = pt.tecnologias_id GROUP BY t.id ORDER BY quantidade DESC", resultSetMapping = "getNumbersPostsByTecnologyMapping")
})

@SqlResultSetMappings(value = {
		@SqlResultSetMapping(name = "getNumbersPostsByTecnologyMapping", classes = {
				@ConstructorResult(targetClass = br.edu.ifrn.portal.dl.utils.PostsTecnologia.class, columns = {
						@ColumnResult(name = "id", type = Long.class),
						@ColumnResult(name = "nome", type = String.class),
						@ColumnResult(name = "quantidade", type = Long.class),
				})
		})
})
public class Tecnologia implements Serializable {

	private static final long serialVersionUID = -780797850805664607L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@NotBlank(message = "O nome é obrigatório!")
	@Size(min = 2, max = 30, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	private String nome;

}
