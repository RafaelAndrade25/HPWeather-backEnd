# HelpTap - Backend

Este é o repositório backend do projeto **HelpTap**. A aplicação é construída com **Java 21** e **Spring Boot 3.x** (v4.0.1 listada no POM), utilizando **PostgreSQL** como banco de dados e **JWT** para segurança e autenticação. A API fornece gerenciamento de usuários, autenticação separada para web e mobile, além de uma integração com a API de clima Open-Meteo.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot** (Web, Data JPA, Security, Validation)
- **PostgreSQL** (Banco de dados relacional)
- **Hibernate** (ORM)
- **JWT (JSON Web Token)** via `jjwt` e `java-jwt` para autenticação segura
- **Lombok** para redução de boilerplate
- **Maven** para gerenciamento de dependências

## ⚙️ Configuração e Instalação

### Pré-requisitos
- Java 21+
- Maven 3.8+
- PostgreSQL rodando localmente ou remotamente

### Variáveis de Ambiente
Antes de rodar a aplicação, você precisará configurar as seguintes variáveis de ambiente (ou adicioná-las diretamente no `application.properties` se for para testes locais):

- `DATABASE_URL`: URL de conexão com o banco (ex: `jdbc:postgresql://localhost:5432/postgres?currentSchema=hpTap`)
- `DATABASE_USERNAME`: Usuário do banco de dados
- `DATABASE_PASSWORD`: Senha do banco de dados
- `JWT_SECRET`: (Opcional) Chave secreta para assinatura dos tokens JWT. Possui fallback padrão no `application.properties`.

*Nota: O banco de dados utiliza o schema `hpTap`.*

### Rodando o Projeto

1. Clone o repositório.
2. Navegue até a pasta do projeto:
   ```bash
   cd HPWeather-backEnd
   ```
3. Compile e rode o projeto com Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
A aplicação iniciará por padrão na porta `8080`.

## 📌 Endpoints da API

Abaixo estão listados os principais endpoints para consumo do front-end. 
*(Para rotas protegidas, é necessário enviar o header `Authorization: Bearer <seu_token>`)*

### Autenticação (`/api/auth`)

| Método | Rota | Descrição | Parâmetros/Body | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/mobile/login` | Realiza o login para usuários do aplicativo mobile. | `LoginRequestDTO` (email, senha) | `LoginResponseDTO` (token, detalhes) |
| `POST` | `/api/auth/web/login` | Realiza o login para usuários do painel web. | `LoginRequestDTO` (email, senha) | `LoginResponseDTO` (token, detalhes) |
| `POST` | `/api/auth/validate` | Valida o token JWT recebido no Header. | Header: `Authorization` | `Integer` (ID do usuário) |

### Usuários (`/api/users`)

| Método | Rota | Descrição | Parâmetros/Body | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/users` | Cria um novo usuário. | `UserCreateDTO` | `UserResponseDTO` |
| `GET` | `/api/users/{id}` | Busca um usuário específico pelo seu ID. | Path: `id` | `UserResponseDTO` |
| `GET` | `/api/users/email/{email}`| Busca um usuário pelo seu e-mail. | Path: `email` | `UserResponseDTO` |
| `GET` | `/api/users` | Busca usuários por sua Role (papel). | Query: `role` (enum) | `List<UserResponseDTO>` |
| `PUT` | `/api/users/{id}` | Atualiza os dados de um usuário existente. | Path: `id`, Body: `UserUpdateDTO`| `UserResponseDTO` |
| `DELETE` | `/api/users/{id}` | Exclui (ou inativa) um usuário. | Path: `id` | `204 No Content` |

### Clima / Tempo (`/api/weather`)

Este endpoint consome internamente a API aberta do [Open-Meteo](https://open-meteo.com/) para entregar a previsão/estado atual do clima na localização desejada.

| Método | Rota | Descrição | Parâmetros/Body | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/api/weather` | Retorna o clima atual de acordo com as coordenadas informadas. | Query: `latitude` (Double), `longitude` (Double) | `OpenMeteoResponseDTO` |

**Exemplo de consumo Weather:**
```http
GET /api/weather?latitude=-23.5505&longitude=-46.6333
```

## 🔒 Segurança

O projeto utiliza **Spring Security** para proteger as rotas da API. A maioria das rotas do sistema (com exceção de criação de conta e login) exige um token JWT válido. Os tokens contêm informações como a data de expiração e *claims* do usuário e devem ser passados no Header da requisição:
`Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...`
