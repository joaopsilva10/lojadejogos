# lojajogos
Loja de Jogos do Seu Manoel com API Web utilizando Java com Spring Boot

# INSTRUÇÕES DE USO DA API na atividade 1, SIGA OS PASSOS ABAIXO:

--- Ao iniciar a aplicação será necessário cria um usuário para realizar o uso da API.
a criação do usuário poderá ser realizada pelo swagger com os seguintes dados:

** endpoint: http://localhost:8080/api/admin/cadastrar-usuario
** json de entrada:
{
  "username": "123",
  "password": "123",
  "role": "ADMIN",
  "dataExpiracao": "2025-06-02T22:48:52.146Z",
  "status": "ATIVO"
}

**saida:
Cadastro realizado!


--- Logo após a criação do usuário, será necessário realizar o login, que poderá ser realizada pelo swagger com os seguintes dados:
** endpoint: http://localhost:8080/api/auth/login
** json de entrada:
{
  "username": "123",
  "password": "123"
}

**json de saida:
{
  "accessToken": "token"
}

--- Em seguinda, copie o token que foi gerado pelo endpoint de login, e use-o para se autenticar no sistemas, 
através do botão Authorize do swagger

--- Dessa forma, você poderá utilizar o restante das funcionalidades da API

--- Para realizar o teste de embalagem, use:

** endpoint: http://localhost:8080/api/admin/embalagem
** json de entrada: 
[
  {
    "produtos": [
      {
        "nome": "Jogo A",
        "altura": 10,
        "largura": 10,
        "comprimento": 10
      },
      {
        "nome": "Jogo B",
        "altura": 20,
        "largura": 20,
        "comprimento": 5
      }
    ]
  }
]

**json de saida:
{"PEDIDO_ID_1": [
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

# INSTRUÇÕES DE USO DA API na atividade 2, SIGA OS PASSOS ABAIXO:
