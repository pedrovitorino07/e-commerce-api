# E-Commerce API

API REST de e-commerce desenvolvida com Java e Spring Boot, implementando autenticação JWT, gerenciamento de produtos, carrinho de compras, pedidos e integração com pagamentos via Stripe.

## Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT (JSON Web Token)
* Stripe API
* Swagger / OpenAPI
* Maven

---

## Funcionalidades

### Autenticação e Autorização

* Registro de usuários
* Login com JWT
* Controle de acesso por roles (`USER` e `ADMIN`)
* Rotas protegidas com Spring Security

### Produtos

* Cadastro de produtos
* Atualização de produtos
* Busca por ID
* Busca por nome
* Busca por categoria
* Paginação
* Soft Delete

### Carrinho de Compras

* Adicionar produtos ao carrinho
* Remover produtos do carrinho
* Consulta do carrinho atual
* Cálculo automático do valor total

### Pedidos

* Checkout do carrinho
* Histórico de pedidos do usuário
* Consulta de pedido por ID
* Controle de status do pedido

### Pagamentos

* Integração com Stripe
* Criação de Payment Intent
* Controle de status do pagamento

### Tratamento de Erros

* Exceptions customizadas
* Global Exception Handler
* Respostas padronizadas para erros

---

## Estrutura do Projeto

```text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── exception
├── enums
└── config
```

---

## Segurança

A API utiliza autenticação baseada em JWT.

Após realizar login, utilize o token retornado no header:

```http
Authorization: Bearer seu_token
```

---

## Documentação Swagger

Após iniciar a aplicação:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Configuração

### Variáveis de Ambiente

```properties
JWT_SECRET=your_jwt_secret
STRIPE_SECRET_KEY=your_stripe_secret_key
```

### Banco de Dados

Configure o PostgreSQL no arquivo:

```yaml
application.yml
```

Exemplo:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce
    username: postgres
    password: password
```

---

## Executando o Projeto

Clone o repositório:

```bash
git clone https://github.com/pedrovitorino07/e-commerce-api.git
```

Acesse a pasta:

```bash
cd e-commerce-api
```

Execute:

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

## Principais Conceitos Aplicados

* REST API
* DTO Pattern
* Repository Pattern
* Service Layer
* Exception Handling
* Authentication & Authorization
* Paginação
* Soft Delete
* Integração com APIs externas
* Controle de estoque
* Processamento de pagamentos

---

## Autor

Pedro Vitorino

* GitHub: https://github.com/pedrovitorino07
* LinkedIn: https://www.linkedin.com

```
```
