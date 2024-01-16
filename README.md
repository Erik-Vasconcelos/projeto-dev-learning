# Blog Dev Learning - Tutoriais e artigos para alunos do IFRN

Este projeto cria um blog para auxiliar os alunos do curso técnico em informática do IFRN, proporcionando um espaço centralizado para a publicação de artigos e tutoriais. Utilizando as melhores práticas de desenvolvimento de software, o processo de construção inicia-se com uma análise detalhada de requisitos, documentados de forma clara, para a compreensão das necessidades do software. A Unified Modeling Language (UML) será empregada para representar visualmente a arquitetura do sistema e facilitar o entendimento. A aplicação foi desenvolvida em Java 17, aproveitando as capacidades do Spring Framework, e para a parte visual, Bootstrap e Thymeleaf.

## :computer: Acesso ao sistema

O sistema está disponível na web através do seguinte link: **http://devlearning.tech**

## 🛠️ Tecnologias e Ferramentas Utilizadas

O Blog Dev Learning foi desenvolvido utilizando as seguintes tecnologias, ferramentas e processos:

- ``Análise de Requisitos`` 
- ``Documentação`` 
- ``UML (Unified Modeling Language)`` 
- ``Protótipo de Interface Gráfica`` **[Link](https://www.figma.com/file/lAyMKIb3BO3JcrsJtp7e0F/PrototipoDevLearning?type=design&node-id=0-1&mode=design)**
- ``PostgreSQL`` 
- ``Java 17`` 
- ``Spring Boot`` 
- ``Spring Data JPA`` 
- ``Spring Security`` 
- ``Spring Validation`` 
- ``Lombok`` 
- ``Maven``
- ``HTML`` 
- ``CSS (Cascading Style Sheets)`` 
- ``JavaScript`` 
- ``Thymeleaf`` 
- ``AWS (Amazon Web Service)``

##  📄 Documentação

Esta seção inclui o **Trabalho de Conclusão de Curso (TCC) anexado**, fornecendo uma visão abrangente do tema, problema e hipótese do projeto. Links diretos são disponibilizados para o Documento de Requisitos e o Documento de Especificação de Requisitos, delineando as necessidades e especificações técnicas do sistema. Adicionalmente, **diversos diagramas, como os de Classe de Domínio, Objetos, Pacotes e Caso de Uso,** oferecem uma compreensão visual da arquitetura e interações no projeto. Explore cada elemento para uma visão completa do projeto.

Consulte **[ProjetoDevLearning](https://drive.google.com/file/d/17mcCWcgsLMH6keT5dP1v7q9SLSS4OFuY/view?usp=sharing)** para saber mais sobre a construção do projeto. 

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

**Clone o Repositório:**  Abra o terminal e execute o seguinte comando:

```git clone https://github.com/Erik-Vasconcelos/projeto-dev-learning.git```

### 📋 Pré-requisitos

De que coisas você precisa para instalar o software e como instalá-lo?

```
Java 17
PostgreSQL
Conta de e-mail no Gmail(Necessário se for utilizar a função de envio de e-mail)
```

### 🔧 Instalação

Uma série de exemplos passo-a-passo que informam o que você deve executar para ter um ambiente de desenvolvimento em execução.

#### 1 - Crie o banco de dados

Dentro do **PgAdmin** crie uma database com o seguinte nome:

```
dev_learning
```



#### 2 - Realize a importação do projeto para dentro de sua IDE e configure

Dentro do **src/main/resources** abra o arquivo **application.properties** e dentro dele realize as seguintes configurações:

#### Conexão com o banco de dados

- Insira o usuário do banco (o padrão é 'postgres'):

```
spring.datasource.username=<usuario>
```

- Insira a senha para o usuário acessar o banco:

```
spring.datasource.password=<senha>
```

- Caso o seu banco esteja rodando em uma porta diferente da padrão (5432), mude essa propriedade na url de conexão: 

```
spring.datasource.url=jdbc:postgresql://localhost:<porta>/dev_learning
```



#### Envio de email

Para usar a funcionalidade de envio de email, realize as seguintes configurações:

**Realize a configuração da sua conta para o Java poder enviar email: [Vídeo no youtube](https://youtu.be/vbKzXIiwe1k?t=356)** 

Após realizar a configuração e cria a senha de app, insira o seu email onde você realizou a configuração:

```
spring.mail.username=<email>
```

Insira a senha que app que foi criada:

```
spring.mail.password=<senha_app_email>
```

Dentro do pacote **src/main/java/service** acesse a classe **EmailService** e mude o 'EMAIL_BLOG':

O EMAIL_BLOG é  conta de email para onde os emails serão enviados (você pode por o e-mail da conta que usou anteriormente)

```java
private static final String EMAIL_BLOG = "<email_destino>";
```



#### 3  - Executando o projeto

Dentro do pacote **src/main/java** acesse a classe **ProjetoDevLearningApplication** e execute ela.

**OBS 1**: Por padrão o sistema irá inserir os registros padrões conforme o diagrama de objetos do projeto, disponível no **[Doc. de requisitos](https://drive.google.com/file/d/18GMYi8PQdijCyC6iQ3byb86NAImB9gzo/view)**. Esse registros são inseridos no método **run()** dentro da classe  **ProjetoDevLearningApplication**.

**OBS 2**: Após executar a aplicação pela primeira vez, comente o método **run()** dentro da classe  **ProjetoDevLearningApplication** para que ao executar a aplicação novamente não venha dar um **erro ao tentar inserir os mesmos registros**.

#### Acessando o sistema

Abra o navegador e digite **localhost:8080** e você poderá navegar pelo sistema.

#### Acessando a área administrativa 

 No navegador digite **localhost:8080/admin** e você poderá acessar com os seguintes usuários: 

**ADMINISTRADOR MASTER** : 

- **login: paulosilva@gmail.com**
- **senha: 458181**

Escritor: 

- **login: luanpereira@gmail.com**
- **senha: 448480**

Você irá acessar o painel com as funcionalidades administrativas do sistema, por exemplo, realizar uma postagem.

## :file_folder: Diagramas

#### Classes de domínio

![Diagrama de classes de domínio](https://github.com/Erik-Vasconcelos/projeto-dev-learning/blob/main/dominio.png)

#### Pacotes

![Diagrama de pacotes](https://github.com/Erik-Vasconcelos/projeto-dev-learning/blob/main/pacotes.png)

## :camera: Imagens

#### Página inicial do blog

![Página inicial](https://github.com/Erik-Vasconcelos/projeto-dev-learning/blob/main/inicio.png)

#### Área administrativa

![Painel administrativo](https://github.com/Erik-Vasconcelos/projeto-dev-learning/blob/main/admin.png)

## ✒️ Autores

* **Erik Vasconcelos** - *Desenvolvedor* - [Linkedin](https://www.linkedin.com/in/erik-vasconcelos/)
* **Mauricio Rabello** - *Orientador* - [Linkedin](https://www.linkedin.com/in/maur%C3%ADcio-rabello-silva-859b1646/)
