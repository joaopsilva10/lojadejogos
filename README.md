# Avaliação Técnica solicitado pela empresa L2

---
## Desenvolvido por
João Patrick

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security
- Banco de dados H2
- Swagger (OpenAPI)
- Docker

---
## Executando com Docker
docker build -t lojadejogos . <br>
docker run -p 8080:8080 lojadejogos

## Documentação Interativa (Swagger)
**Acesse:**
http://localhost:8080/swagger-ui/index.html

## Funcionalidade Principal do EXERCÍCIO 1 - EMPACOTAMENTO

## Loja de Jogos - API de Embalagem

Projeto Java Spring Boot que simula o sistema de pedidos da loja de jogos do Seu Manoel, com foco na **automatização do processo de embalagem** dos pedidos com base nas dimensões dos produtos.<br>

A API é responsável por:
- Receber pedidos com uma lista de produtos e suas dimensões
- Calcular o melhor encaixe possível dos produtos em caixas de papelão
- Retornar a alocação ideal para cada pedido e caixa

Caixas disponíveis:
- **Caixa 1:** 30 x 40 x 80
- **Caixa 2:** 80 x 50 x 40
- **Caixa 3:** 50 x 80 x 60

---

## Como Usar a API (Atividade 1)

### 1 Criar um usuário

**Acesse via Swagger:**
POST http://localhost:8080/api/admin/cadastrar-usuario

**JSON de entrada:**
{
  "username": "123",
  "password": "123",
  "role": "ADMIN",
  "dataExpiracao": "2025-06-02T22:48:52.146Z",
  "status": "ATIVO"
}

**Resposta esperada:**
Cadastro realizado!

### 2 Realizar login e obtem token

**Acesse via Swagger:**
POST http://localhost:8080/api/auth/login

**JSON de entrada:**
{
  "username": "123",
  "password": "123"
}

**Resposta esperada:**
{
  "accessToken": "seu_token_jwt_aqui"
}

### 3 Autenticar-se no Swagger
Para isso, use o próprio botão de Authorize do SWAGGER e cole o accessToken que você copiou.

### 4 Realizar teste de embalagem
POST http://localhost:8080/api/admin/embalagem

**JSON de entrada:**
[
  {
    "produtos": [
      {
        "nome": "Controle PS5",
        "altura": 10,
        "largura": 15,
        "comprimento": 20
      },
      {
        "nome": "Headset Gamer",
        "altura": 25,
        "largura": 30,
        "comprimento": 20
      }
    ]
  }
]

**Resposta esperada:**
{
  "PEDIDO_ID_1": [
    {
      "Caixa 1": [
        {
          "id": 2,
          "nome": "Jogo B",
          "altura": 20,
          "largura": 20,
          "comprimento": 5,
          "pedido": null,
          "volume": 2000
        },
        {
          "id": 1,
          "nome": "Jogo A",
          "altura": 10,
          "largura": 10,
          "comprimento": 10,
          "pedido": null,
          "volume": 1000
        }
      ]
    }
  ]
}