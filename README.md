# crud-produtos

# Produto Controller

Este é o controlador responsável por lidar com as requisições relacionadas aos produtos da aplicação.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **PostgreSQL e PgAdmin 4**
- **Maven**
- **Hibernate e JPA**
- **Hateoas**
- **Lombok**
- **Mockito**
- **OpenCsv**

## Configuração do Banco de Dados PostgreSQL

Antes de executar o projeto, é necessário configurar e iniciar o banco de dados PostgreSQL. Siga as etapas abaixo para realizar essa configuração:

1. Baixe e instale o PostgreSQL de acordo com o seu sistema operacional. Você pode encontrar o instalador oficial no site oficial do PostgreSQL: [https://www.postgresql.org/download/](https://www.postgresql.org/download/).

2. Após a instalação, inicie o serviço do PostgreSQL.

3. Por padrão, o PostgreSQL cria um banco de dados chamado "postgres" e um usuário chamado "postgres" com privilégios de superusuário. Abra o terminal ou prompt de comando e execute o seguinte comando para acessar o console do PostgreSQL: psql -U postgres

4. No console do PostgreSQL, crie um novo banco de dados para a aplicação executando o seguinte comando: CREATE DATABASE nome_do_banco_de_dados;
Substitua "nome_do_banco_de_dados" pelo nome que você deseja atribuir ao banco de dados da aplicação.

5. Crie um novo usuário para a aplicação executando o seguinte comando: CREATE USER nome_do_usuario WITH ENCRYPTED PASSWORD 'senha_do_usuario'; Substitua "nome_do_usuario" pelo nome que você deseja atribuir ao usuário da aplicação e "senha_do_usuario" pela senha desejada para o usuário.

6. Conceda privilégios ao usuário para o banco de dados da aplicação executando o seguinte comando: GRANT ALL PRIVILEGES ON DATABASE nome_do_banco_de_dados TO nome_do_usuario; Substitua "nome_do_banco_de_dados" pelo nome do banco de dados criado anteriormente e "nome_do_usuario" pelo nome do usuário criado anteriormente.

7. Saia do console do PostgreSQL executando o seguinte comando: \q


## Configuração das Propriedades do PostgreSQL

Após configurar o banco de dados PostgreSQL, é necessário atualizar as propriedades do PostgreSQL no arquivo de propriedades da aplicação. Siga as etapas abaixo para realizar essa configuração:

1. Abra o arquivo `application.properties` ou `application.yml` do projeto. Esse arquivo está localizado no diretório de recursos do projeto.

2. Localize as propriedades relacionadas ao PostgreSQL e atualize-as de acordo com as informações do seu banco de dados configurado anteriormente. As propriedades comuns incluem:

- `spring.datasource.url`: URL de conexão com o banco de dados PostgreSQL. Geralmente no formato `jdbc:postgresql://localhost:5432/nome_do_banco_de_dados`, onde "localhost" é o endereço do servidor do banco de dados e "nome_do_banco_de_dados" é o nome do banco de dados que você criou.

- `spring.datasource.username`: Nome de usuário do banco de dados PostgreSQL. Geralmente "postgres" ou o nome do usuário que você criou.

- `spring.datasource.password`: Senha do usuário do banco de dados PostgreSQL. A senha que você atribuiu ao usuário.

3. Salve as alterações no arquivo `application.properties` ou `application.yml`.

## Métodos

### Obtém um produto pelo seu ID

`GET /api/produtos/{id}`

Parâmetros de Path:
- `id` (UUID): ID do produto a ser obtido.

Respostas:
- 200 OK: Produto encontrado. Retorna o objeto `ProdutoModel` no corpo da resposta.
- 404 Not Found: Produto não encontrado.

### Obtém todos os produtos

`GET /api/produtos`

Respostas:
- 200 OK: Retorna a lista de produtos no corpo da resposta.
- 404 Not Found: Não existem produtos cadastrados.

### Cria um novo produto

`POST /api/produtos`

Corpo da Requisição:
- Objeto `ProdutoModelDTO` contendo os dados do produto a ser criado.

Respostas:
- 201 Created: Produto criado com sucesso. Retorna o objeto `ProdutoModel` no corpo da resposta.

### Processa o upload de um arquivo CSV de produtos

`POST /api/produtos/upload`

Parâmetros de Formulário:
- `file` (MultipartFile): Arquivo CSV a ser processado.

Respostas:
- 200 OK: Arquivo CSV processado com sucesso.

### Exclui um produto pelo seu ID

`DELETE /api/produtos/{id}`

Parâmetros de Path:
- `id` (UUID): ID do produto a ser excluído.

Respostas:
- 204 No Content: Produto excluído com sucesso.
- 404 Not Found: Produto não encontrado.

### Atualiza um produto pelo seu ID

`PUT /api/produtos/{id}`

Parâmetros de Path:
- `id` (UUID): ID do produto a ser atualizado.

Corpo da Requisição:
- Objeto `ProdutoModelDTO` contendo os dados atualizados do produto.

Respostas:
- 200 OK: Produto atualizado com sucesso. Retorna o objeto `ProdutoModel` no corpo da resposta.
- 404 Not Found: Produto não encontrado.

##  Dependencias do projeto

- **spring-boot-starter-hateoas**: [Link](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-hateoas)
- **spring-boot-starter-web**: [Link](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- **spring-boot-starter-data-jpa**: [Link](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
- **hibernate-validator**: [Link](https://mvnrepository.com/artifact/org.hibernate/hibernate-validator)
- **opencsv**: [Link](https://mvnrepository.com/artifact/com.opencsv/opencsv)
- **commons-io**: [Link](https://mvnrepository.com/artifact/org.apache.commons/commons-io)
- **jackson-dataformat-csv**: [Link](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-csv)
- **commons-lang3**: [Link](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3)
- **slf4j-api**: [Link](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)
- **mockito-junit-jupiter**: [Link](https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter)
- **spring-boot-starter-validation**: [Link](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation)
- **postgresql**: [Link](https://mvnrepository.com/artifact/org.postgresql/postgresql)
- **spring-boot-starter-test**: [Link](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)
- **lombok**: [Link](https://mvnrepository.com/artifact/org.projectlombok/lombok)

## Autor

Esse projeto foi desenvolvido por Gustavo Henrique Zenke.

Você pode me encontrar em outras plataformas:

- Instagram: [@gustavozenke](https://www.instagram.com/gustavozenke/)
- LinkedIn: [Gustavo Henrique Zenke](https://linkedin.com/in/gustavo-henrique-zenke) 

Fique à vontade para entrar em contato ou contribuir com o projeto!
