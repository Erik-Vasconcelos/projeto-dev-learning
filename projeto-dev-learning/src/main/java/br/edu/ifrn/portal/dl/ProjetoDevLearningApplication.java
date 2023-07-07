package br.edu.ifrn.portal.dl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Projeto criado com requisito parcial de conclusão do curso técnico em informática do 
 * <strong>IFRN - Instituto Federal de Educação Ciência e Tecnologia do Rio Grande do Norte</strong> 
 * campus Nova Cruz.
 * O projeto tem como objetivo criar um blog para auxiliar estudantes do curso técnico em informática do IFRN 
 * no aprendizado de disciplinas sobre desemvolvimento do software, disponibilizando um ambiente onde é possível
 *  acessar as disciplinas e postagens artigos de tutoriais relacionadas a mesma.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-08
 * @version 0.1
 */

@SpringBootApplication
public class ProjetoDevLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoDevLearningApplication.class, args);
	}

}
