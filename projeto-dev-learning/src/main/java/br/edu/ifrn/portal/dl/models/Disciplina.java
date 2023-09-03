package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Classe modelo da <strong>entidade Disciplina<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-13
 * @version 1.0 2023-09-03
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "disciplinas")
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Column(nullable = false, unique = true)
	private String nome;
	
	@NonNull
	@Column(nullable = false, length = 1000)
	private String descricaoObjetivos;
	
	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT", length = 4194304)
	private String imagem;

}
