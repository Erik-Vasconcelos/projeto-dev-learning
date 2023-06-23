package br.edu.ifrn.portal.dl.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Tecnologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@NotBlank(message = "O nome é obrigatório!")
	@Size(min = 2, max = 30, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	private String nome;

}
