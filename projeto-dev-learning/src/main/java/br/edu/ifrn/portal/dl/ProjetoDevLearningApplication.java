package br.edu.ifrn.portal.dl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.portal.dl.models.Disciplina;
import br.edu.ifrn.portal.dl.models.Gerenciador;
import br.edu.ifrn.portal.dl.models.Postagem;
import br.edu.ifrn.portal.dl.models.Role;
import br.edu.ifrn.portal.dl.models.Tecnologia;
import br.edu.ifrn.portal.dl.models.enuns.TipoGerencidor;
import br.edu.ifrn.portal.dl.models.enuns.TipoPostagem;
import br.edu.ifrn.portal.dl.repositories.DisciplinaRepository;
import br.edu.ifrn.portal.dl.repositories.GerenciadorRepository;
import br.edu.ifrn.portal.dl.repositories.PostagemRepository;
import br.edu.ifrn.portal.dl.repositories.RoleRepository;
import br.edu.ifrn.portal.dl.repositories.TecnologiaRepository;
import br.edu.ifrn.portal.dl.services.RoleService;
import br.edu.ifrn.portal.dl.utils.ImagemUtil;

/**
 * Projeto criado como requisito parcial de conclusão do curso técnico em
 * informática do <strong>IFRN - Instituto Federal de Educação Ciência e
 * Tecnologia do Rio Grande do Norte</strong> campus Nova Cruz. O projeto tem
 * como objetivo criar um blog para auxiliar estudantes do curso técnico em
 * informática do IFRN no aprendizado de conteúdos das disciplinas sobre
 * desenvolvimento do software, disponibilizando um ambiente onde é possível
 * acessar as postagens referentes aos conteúdo da ementa proposta pela
 * instituição no referente curso.
 * 
 * @author Erik Vasconcelos
 * @since 2023-06-08
 * @version 1.1 2024-01-15
 */

@SpringBootApplication
public class ProjetoDevLearningApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private GerenciadorRepository gerenciadorRepository;

	@Autowired
	private TecnologiaRepository tecnologiaRepository;

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private PostagemRepository postagemRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoDevLearningApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role r1 = new Role("ROLE_ESCRITOR", 1);
		Role r2 = new Role("ROLE_ADMIN", 2);
		Role r3 = new Role("ROLE_ADMIN_MASTER", 3);

		roleRepository.saveAll(Arrays.asList(r1, r2, r3));

		String imagemAdmin = ImagemUtil.obterImagemBase64("admin.jpg");
		String senhaAdmin = new BCryptPasswordEncoder().encode("458181");

		Gerenciador admin = new Gerenciador(TipoGerencidor.ADMIN_MASTER, "Paulo Silva", "paulosilva@gmail.com",
				senhaAdmin, imagemAdmin);

		admin.getRoles().addAll(roleService.obterRolesPorTipoGerenciador(admin.getTipoGerenciador()));

		String imagemEscritor = ImagemUtil.obterImagemBase64("escritor.jpg");
		String senhaEscritor = new BCryptPasswordEncoder().encode("448480");

		Gerenciador escritor = new Gerenciador(TipoGerencidor.ESCRITOR, "Luan Pereira", "luanpereira@gmail.com",
				senhaEscritor, imagemEscritor);

		escritor.getRoles().addAll(roleService.obterRolesPorTipoGerenciador(escritor.getTipoGerenciador()));

		gerenciadorRepository.saveAll(Arrays.asList(admin, escritor));

		Tecnologia t1 = new Tecnologia("Java");
		Tecnologia t2 = new Tecnologia("JavaScript");
		Tecnologia t3 = new Tecnologia("Html");
		Tecnologia t4 = new Tecnologia("Spring");

		tecnologiaRepository.saveAll(Arrays.asList(t1, t2, t3, t4));

		String descricaoDisciplina1 = "A disciplina de Orientação a Objetos visa proporcionar uma compreensão"
				+ " aprofundada dos princípios e práticas da programação orientada a objetos, capacitando os"
				+ " alunos a projetar, implementar e manter sistemas de software eficientes";
		String imgDisciplina1 = ImagemUtil.obterImagemBase64("disciplina1.png");

		Disciplina d1 = new Disciplina("POO Programação Orientada a Objetos", descricaoDisciplina1, imgDisciplina1);

		String descricaoDisciplina2 = "\r\n"
				+ "A disciplina de Autoria Web visa capacitar os estudantes na criação de conteúdo digital interativo para a web,"
				+ " abrangendo conceitos de design, programação e usabilidade.";
		String imgDisciplina2 = ImagemUtil.obterImagemBase64("disciplina2.png");

		Disciplina d2 = new Disciplina("Autoria web", descricaoDisciplina2, imgDisciplina2);

		disciplinaRepository.saveAll(Arrays.asList(d1, d2));

		String imgPostagem1 = ImagemUtil.obterImagemBase64("postagem1.jpeg");
		Postagem p1 = new Postagem(TipoPostagem.ARTIGO, "A linguagem Java", imgPostagem1,
				"Java é uma linguagem Orientada a objetos", "<p><span style=\"color: rgb(187, 187, 187);\">Java é uma linguagem Orientada a objetos</span></p>",
				"<p><span style=\"color: rgb(187, 187, 187);\">Java é uma linguagem Orientada a objetos</span></p>", d1, LocalDate.parse("2023-09-15"));

		p1.setTecnologias(new LinkedHashSet<Tecnologia>(Arrays.asList(t1)));
		p1.setAutor(admin);

		String imgPostagem2 = ImagemUtil.obterImagemBase64("postagem2.jpg");
		Postagem p2 = new Postagem(TipoPostagem.ARTIGO, "Criando uma aplicação em Spring MVC", imgPostagem2,
				"Primerio vamos criar", "<p><span style=\"color: rgb(187, 187, 187);\">Primerio vamos criar</span></p>", "<p><span style=\"color: rgb(187, 187, 187);\">Primerio vamos criar</span></p>", d1,
				LocalDate.parse("2023-09-12"));

		p2.setTecnologias(new LinkedHashSet<Tecnologia>(Arrays.asList(t2, t3)));
		p2.setAutor(escritor);

		postagemRepository.saveAll(Arrays.asList(p1, p2));

	}

}
