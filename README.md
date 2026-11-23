# projeto-desafio-fitness

Front end (Java + JSP) do Desafio Fitness. Não acessa banco de dados: todas as telas consomem a API REST [`projeto-desafio-fitness-api`](https://github.com/LeandroArantesDev/projeto-desafio-fitness-api) por HTTP, enviando o token JWT do usuário logado.

Arquitetura em camadas: **Controller → Service → DAO → API**. A estrutura de pastas está explicada em [`ESTRUTURA.txt`](ESTRUTURA.txt).

## Pré-requisito

A API precisa estar rodando em `http://localhost:8080/projeto-desafio-fitness/api` (veja o README da API). O endereço fica em `src/main/java/com/fitness/config/ApiSingleton.java` (`BASE_URL`).

## Como rodar

Gerar o build do projeto (gera `deploy/projeto-desafio-fitness.war`):

```bash
mvn clean package
```

Subir o container (Tomcat):

```bash
docker compose up -d
```

Parar o container:

```bash
docker compose down
```

Acesse: `http://localhost:8081/projeto-desafio-fitness/`

Usuário de teste: `admin@gmail.com` com a senha `password` (criado pelo `database.sql` da API).

## Telas

| Tela | Rota | O que faz |
|---|---|---|
| Login | `/login` | Autentica na API e guarda o token na sessão |
| Cadastro | `/register` | Cria um usuário comum |
| Home | `/home` | Lista os desafios com participação e progresso do usuário |
| Desafios | `/desafios` | Desafios criados e em que participa; criar, editar, excluir e participar |
| Progresso | `/progresso?desafioId={id}` | Histórico, total e registro de progresso em um desafio |
| Usuários | `/usuarios` | CRUD de usuários (somente admin) |
| Sair | `/logout` | Encerra a sessão |
