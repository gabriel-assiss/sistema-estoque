# 📘 Documentação da API - Sistema de Controle de Estoque

Esta documentação foi elaborada especialmente para os desenvolvedores **Front-End**, detalhando todas as regras de negócio, autenticação, modelos de requisição/resposta, parâmetros, códigos de status HTTP e exemplos práticos de integração.

---

## 📑 Sumário

1. [Visão Geral & Configurações Base](#1-visão-geral--configurações-base)
2. [Autenticação e Segurança (JWT)](#2-autenticação-e-segurança-jwt)
3. [Padrões de Resposta & Paginação](#3-padrões-de-resposta--paginação)
4. [Tratamento de Erros](#4-tratamento-de-erros)
5. [Tabelas de Enums](#5-tabelas-de-enums)
6. [Módulos e Endpoints](#6-módulos-e-endpoints)
   - [6.1. Autenticação (`/auth`)](#61-autenticação-auth)
   - [6.2. Funcionários (`/funcionarios`)](#62-funcionários-funcionarios)
   - [6.3. Categorias (`/categorias`)](#63-categorias-categorias)
   - [6.4. Fornecedores (`/fornecedores`)](#64-fornecedores-fornecedores)
   - [6.5. Produtos (`/produtos`)](#65-produtos-produtos)
   - [6.6. Estoque (`/estoques`)](#66-estoque-estoques)
   - [6.7. Compras (`/compras`)](#67-compras-compras)
   - [6.8. Movimentações de Estoque (`/movimentacoes`)](#68-movimentações-de-estoque-movimentacoes)
7. [Exemplo de Configuração no Front-End (Axios / TypeScript)](#7-exemplo-de-configuração-no-front-end-axios--typescript)
8. [Pontos de Atenção & Alertas para o Front-End](#8-pontos-de-atenção--alertas-para-o-front-end)

---

## 1. Visão Geral & Configurações Base

- **URL Base:** `http://localhost:8080`
- **Formato dos Dados:** JSON (`application/json`)
- **Porta Padrão:** `8080`
- **Fuso / Datas:** Formato ISO 8601 (`YYYY-MM-DD`)
- **CORS Liberado:** Sim, para a origem `http://localhost:5173` (Vite)

> [!NOTE]
> A API utiliza o Spring Boot 3 com Spring Security e JWT. Todas as rotas (com exceção do login) exigem que o token JWT seja enviado no cabeçalho HTTP.

---

## 2. Autenticação e Segurança (JWT)

### Cabeçalho de Autorização
Para todas as requisições autenticadas, inclua o cabeçalho:
```http
Authorization: Bearer <SEU_TOKEN_JWT_AQUI>
```

### Perfis de Acesso (`Roles`)
- **`ADMIN`**: Acesso completo a todas as rotas e operações.
- **`PADRAO`**: Acesso a produtos, categorias, fornecedores, compras, estoques e movimentações.

> [!WARNING]
> No backend atual, a rota `/funcionarios/**` requer perfil **`ADMIN`**. Usuários com perfil `PADRAO` receberão `403 Forbidden` ao tentar interagir com endpoints de funcionários.

### Duração do Token
- O token gerado no login tem validade de **2 horas**.
- Após a expiração, a API retornará `403 Forbidden` ou falha de autenticação, devendo o front-end redirecionar para a tela de login.

---

## 3. Padrões de Resposta & Paginação

### Paginação do Spring Data (`Pageable`)
Endpoints de listagem recebem parâmetros de consulta (`query params`):
- `page` *(opcional, int, padrão: 0)*: Número da página (inicia em `0`).
- `size` *(opcional, int, padrão: 20)*: Quantidade de itens por página.
- `sort` *(opcional, string)*: Ordenação, formato: `campo,asc` ou `campo,desc` (ex: `nome,asc`).

#### Estrutura do Objeto Paginado de Retorno:
```json
{
  "content": [ /* lista dos itens retornados */ ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": { "sorted": false, "unsorted": true, "empty": true },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 45,
  "totalPages": 5,
  "last": false,
  "size": 10,
  "number": 0,
  "numberOfElements": 10,
  "first": true,
  "empty": false
}
```

---

## 4. Tratamento de Erros

A API possui um manipulador global (`GlobalExceptionHandler`) com os seguintes comportamentos:

| Código HTTP | Cenário | Formato da Resposta |
| :--- | :--- | :--- |
| **`400 Bad Request`** | Erro de validação de formulário (campos inválidos) | Objeto JSON mapeando cada campo à mensagem de erro. |
| **`400 Bad Request`** | Parâmetro inválido (`IllegalArgumentException`) | `{"mensagem": "Requisição inválida."}` |
| **`400 Bad Request`** | Mudança de status proibida (`StatusInvalidoException`) | Texto simples com a descrição (ex: `"Status Inválido"`) |
| **`403 Forbidden`** | Token ausente, inválido ou perfil sem permissão | Resposta padrão do Spring Security (`403`) |
| **`404 Not Found`** | Recurso não localizado por ID ou Nome | Texto simples com a mensagem (ex: `"Produto não encontrado"`) |
| **`409 Conflict`** | Registro duplicado ou estoque insuficiente | Texto simples com a mensagem (ex: `"Fornecedor já existe"`, `"Estoque insuficiente..."`) |
| **`409 Conflict`** | Concorrência de edição (`OptimisticLock`) | `{"erro": "Conflito de atualização.", "mensagem": "..."}` |

#### Exemplo de Erro de Validação (`400 Bad Request`):
```json
{
  "email": "E-mail deve ser válido",
  "senha": "Senha não pode ser vazia"
}
```

---

## 5. Tabelas de Enums

### `RoleFuncionario`
- `ADMIN`: Administrador do sistema
- `PADRAO`: Operador / Funcionário padrão

### `StatusFuncionario`, `StatusCategoria`, `StatusFornecedor`, `StatusProduto`
- `ATIVO`: Ativo no sistema
- `INATIVO`: Desativado / inativo

### `StatusCompra`
- `CONCLUIDA`: Compra efetuada e estoque alimentado
- `CANCELADA`: Compra cancelada e estoque estornado

### `TipoMovimentacao`
- `ENTRADA`: Entrada de produtos no estoque
- `SAIDA`: Saída de produtos
- `MUDANCA`: Ajuste ou alteração de saldo

---

## 6. Módulos e Endpoints

---

### 6.1. Autenticação (`/auth`)

#### `POST /auth/login`
Autentica o usuário e retorna o token de acesso.
- **Autenticação:** Não requer (pública)
- **Request Body:**
```json
{
  "email": "admin@estoque.com",
  "senha": "senha123"
}
```
- **Response `200 OK`:**
```json
{
  "id": 1,
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI...",
  "email": "admin@estoque.com",
  "nome": "Administrador",
  "role": "ADMIN"
}
```

---

### 6.2. Funcionários (`/funcionarios`)
> **Acesso:** Exclusivo para usuários com `role: "ADMIN"`.

#### `POST /funcionarios/salvar`
Cadastra um novo funcionário.
- **Request Body:**
```json
{
  "nome": "Carlos Silva",
  "role": "PADRAO",
  "email": "carlos@estoque.com",
  "senha": "senhaSegura123",
  "cargo": "Almoxarife",
  "matricula": "MAT-2026-001"
}
```
- **Response `201 Created`:**
```json
{
  "id": 2,
  "nome": "Carlos Silva",
  "matricula": "MAT-2026-001",
  "cargo": "Almoxarife",
  "role": "PADRAO",
  "statusFuncionario": "ATIVO",
  "email": "carlos@estoque.com"
}
```

#### `GET /funcionarios/todos`
Retorna a lista paginada de todos os funcionários.
- **Query Params:** `page`, `size`, `sort`
- **Response `200 OK`:** `Page<FuncionarioResponseDTO>`

#### `GET /funcionarios/buscaid/{id}`
Localiza um funcionário específico pelo ID.
- **Path Param:** `id` (Long)
- **Response `200 OK`:** `FuncionarioResponseDTO`
- **Erros:** `404 Not Found` se não existir.

#### `GET /funcionarios/buscanome/{nome}`
Filtra funcionários por parte do nome (case-insensitive).
- **Path Param:** `nome` (String)
- **Query Params:** `page`, `size`, `sort`
- **Response `200 OK`:** `Page<FuncionarioDTO>`

#### `PUT /funcionarios/atualizanome/{id}?nome={novoNome}`
Atualiza o nome de um funcionário.
- **Path Param:** `id` (Long)
- **Query Param:** `nome` (String obrigatória na URL)
- **Response `200 OK`:** `FuncionarioResponseDTO`

#### `PUT /funcionarios/inativar/{id}`
Altera o status do funcionário para `INATIVO`.
- **Path Param:** `id` (Long)
- **Response `200 OK`:** `FuncionarioResponseDTO`

#### `PUT /funcionarios/ativar/{id}`
Altera o status do funcionário para `ATIVO`.
- **Path Param:** `id` (Long)
- **Response `200 OK`:** `FuncionarioResponseDTO`

---

### 6.3. Categorias (`/categorias`)
> **Acesso:** `ADMIN` e `PADRAO`.

#### `POST /categorias`
Cadastra uma nova categoria.
- **Request Body:**
```json
{
  "nome": "Eletrônicos"
}
```
- **Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "Eletrônicos",
  "statusCategoria": "ATIVO"
}
```

#### `GET /categorias`
Listagem paginada de todas as categorias.
- **Query Params:** `page`, `size`, `sort`
- **Response `200 OK`:** `Page<CategoriaResponseDTO>`

#### `GET /categorias/{id}`
Busca categoria pelo ID.
- **Path Param:** `id` (Long)
- **Response `200 OK`:** `CategoriaResponseDTO`

#### `GET /categorias/ativas`
Listagem paginada apenas das categorias ativas.
- **Response `200 OK`:** `Page<CategoriaResponseDTO>`

#### `GET /categorias/inativas`
Listagem paginada apenas das categorias inativas.
- **Response `200 OK`:** `Page<CategoriaResponseDTO>`

#### `GET /categorias/nome/{nome}`
Filtro paginado por nome da categoria.
- **Path Param:** `nome` (String)
- **Response `200 OK`:** `Page<CategoriaDTO>`

#### `PUT /categorias/{id}`
Atualiza dados da categoria (nome e/ou status).
- **Path Param:** `id` (Long)
- **Request Body:**
```json
{
  "nome": "Eletrônicos e Informática",
  "statusCategoria": "ATIVO"
}
```
- **Response `200 OK`:** `CategoriaResponseDTO`

#### `DELETE /categorias/{id}`
Exclui fisicamente a categoria.
- **Path Param:** `id` (Long)
- **Response `204 No Content`** (corpo vazio)

---

### 6.4. Fornecedores (`/fornecedores`)
> **Acesso:** `ADMIN` e `PADRAO`.

#### `POST /fornecedores`
Cadastra um novo fornecedor.
- **Request Body:**
```json
{
  "nome": "Distribuidora Nacional LTDA",
  "cnpj": "12.345.678/0001-90"
}
```
- **Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "Distribuidora Nacional LTDA",
  "cnpj": "12.345.678/0001-90",
  "statusFornecedor": "ATIVO"
}
```
- **Erros:** `409 Conflict` se o CNPJ já estiver cadastrado.

#### `GET /fornecedores`
Lista paginada de fornecedores.
- **Response `200 OK`:** `Page<FornecedorResponseDTO>`

#### `GET /fornecedores/{id}`
Busca fornecedor pelo ID.
- **Path Param:** `id` (Long)
- **Response `200 OK`:** `FornecedorResponseDTO`

#### `GET /fornecedores/nome/{nome}`
Filtra fornecedores pelo nome.
- **Path Param:** `nome` (String)
- **Response `200 OK`:** `Page<FornecedorDTO>`

#### `PUT /fornecedores/{id}/nome?nome={novoNome}`
Atualiza o nome do fornecedor.
- **Path Param:** `id` (Long)
- **Query Param:** `nome` (String obrigatória)
- **Response `200 OK`:** `FornecedorResponseDTO`

#### `PUT /fornecedores/{id}/inativar`
Inativa o fornecedor.
- **Response `200 OK`:** `FornecedorResponseDTO`

#### `PUT /fornecedores/{id}/ativar`
Ativa o fornecedor.
- **Response `200 OK`:** `FornecedorResponseDTO`

---

### 6.5. Produtos (`/produtos`)
> **Acesso:** `ADMIN` e `PADRAO`.

#### `POST /produtos?quantidade={qtd}`
Cadastra um produto e inicializa seu estoque com a quantidade informada.

> [!IMPORTANT]
> O parâmetro `quantidade` **deve obrigatoriamente** ser passado como **Query Parameter** na URL (`?quantidade=X`), e deve ser um número positivo (> 0).

- **URL:** `/produtos?quantidade=50`
- **Request Body:**
```json
{
  "nome": "Teclado Mecânico RGB",
  "unidadeMedida": "UN",
  "quantidade": 50,
  "categoriaId": 1
}
```
- **Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "Teclado Mecânico RGB",
  "unidadeMedida": "UN",
  "statusProduto": "ATIVO",
  "categoriaId": 1,
  "categoriaNome": "Eletrônicos",
  "estoqueQuantidade": 50
}
```

#### `GET /produtos`
Listagem paginada de todos os produtos.
- **Response `200 OK`:** `Page<ProdutoResponseDTO>`

#### `GET /produtos/{id}`
Busca produto pelo ID.
- **Response `200 OK`:** `ProdutoResponseDTO`

#### `GET /produtos/nome/{nome}`
Filtra produtos pelo nome.
- **Response `200 OK`:** `Page<ProdutoDTO>`

#### `GET /produtos/categoria/{categoriaNome}`
Filtra produtos por nome da categoria vinculada.
- **Path Param:** `categoriaNome` (String)
- **Response `200 OK`:** `Page<ProdutoResponseDTO>`

#### `PUT /produtos/{id}/inativar`
Inativa o produto. Se já estiver inativo, retorna `400 Bad Request`.
- **Response `200 OK`:** `ProdutoResponseDTO`

#### `PUT /produtos/{id}/ativar`
Ativa o produto. Se já estiver ativo, retorna `400 Bad Request`.
- **Response `200 OK`:** `ProdutoResponseDTO`

#### `DELETE /produtos/{id}`
Exclui o produto pelo ID.
- **Response `204 No Content`**

---

### 6.6. Estoque (`/estoques`)
> **Acesso:** `ADMIN` e `PADRAO`.

#### `GET /estoques`
Lista o saldo de estoque de todos os produtos cadastrados.
- **Response `200 OK`:**
```json
{
  "content": [
    {
      "id": 1,
      "quantidadeEstoque": 50,
      "produtoId": 1
    }
  ]
}
```

#### `GET /estoques/{id}`
Consulta o registro de estoque pelo ID do estoque.
- **Response `200 OK`:** `EstoqueResponseDTO`

#### `PUT /estoques/adicionar/{idProduto}?quantidade={qtd}`
Adiciona quantidade ao estoque do produto indicado.
- **Path Param:** `idProduto` (Long)
- **Query Param:** `quantidade` (int > 0)
- **Response `200 OK`** (sem corpo)

#### `PUT /estoques/subtrair/{idProduto}?quantidade={qtd}`
Subtrai quantidade do estoque do produto.
- **Path Param:** `idProduto` (Long)
- **Query Param:** `quantidade` (int > 0)
- **Response `200 OK`** (sem corpo)
- **Erros:** `409 Conflict` se a quantidade solicitada for maior que o saldo em estoque.

---

### 6.7. Compras (`/compras`)
> **Acesso:** `ADMIN` e `PADRAO`.

#### `POST /compras`
Registra uma nova compra de mercadorias.
- Atualiza o estoque somando os itens adquiridos.
- Registra movimentações de estoque do tipo `ENTRADA`.
- Define o status da compra como `CONCLUIDA`.

- **Request Body:**
```json
{
  "funcionario": 1,
  "fornecedor": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 10,
      "precoUnitario": 199.90
    },
    {
      "produtoId": 2,
      "quantidade": 5,
      "precoUnitario": 89.50
    }
  ]
}
```AAAAAEstoque>`

#### `GET /movimentacoes/produto/{produtoId}/funcionario/{funcionarioId}`
Filtra movimentações cruzando produto e funcionário.
- **Path Params:** `produtoId` (Long), `funcionarioId` (Long)
- **Response `200 OK`:** `Page<MovimentacaoEstoque>`

---

## 7. Exemplo de Configuração no Front-End (Axios / TypeScript)

Abaixo está um exemplo de configuração com interceptores para injetar o token JWT automaticamente e tratar erros:

```typescript
// api.ts
import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor de Requisição: Injeta o Token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('@estoque:token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor de Resposta: Redireciona em caso de 403 / sessão expirada
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 403) {
      localStorage.removeItem('@estoque:token');
      localStorage.removeItem('@estoque:user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
```

### Exemplo de Chamada de Login:
```typescript
interface LoginResponse {
  id: number;
  token: string;
  email: string;
  nome: string;
  role: 'ADMIN' | 'PADRAO';
}

export async function login(email: string, senha: string): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>('/auth/login', { email, senha });
  localStorage.setItem('@estoque:token', data.token);
  localStorage.setItem('@estoque:user', JSON.stringify(data));
  return data;
}
```

### Exemplo de Cadastro de Produto:
```typescript
export async function cadastrarProduto(produto: {
  nome: string;
  unidadeMedida: string;
  quantidade: number;
  categoriaId: number;
}) {
  // ATENÇÃO: quantidade passada na Query String (?quantidade=X)
  const { data } = await api.post(
    `/produtos?quantidade=${produto.quantidade}`,
    produto
  );
  return data;
}
```

---

## 8. Pontos de Atenção & Alertas para o Front-End

> [!IMPORTANT]
> 1. **Parâmetros de Query em Requisições PUT e POST:**
>    - `POST /produtos?quantidade={qtd}`: Exige `quantidade` na query da URL, além do corpo da requisição.
>    - `PUT /funcionarios/atualizanome/{id}?nome={novoNome}`: O novo nome é passado via Query Param.
>    - `PUT /fornecedores/{id}/nome?nome={novoNome}`: O novo nome é passado via Query Param.
>    - `PUT /compras/{id}/cancelar?idFuncionario={idFuncionario}`: O ID do funcionário deve ir na URL.
>    - `PUT /estoques/adicionar/{idProduto}?quantidade={qtd}` e `subtrair`: A quantidade deve ir na URL.

> [!NOTE]
> 2. **CORS (Cross-Origin Resource Sharing) Liberado:**
>    O CORS está configurado e habilitado no backend para a aplicação Front-End:
>    - **Origem permitida:** `http://localhost:5173` (porta padrão do Vite / React / Vue).
>    - **Métodos permitidos:** `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`.
>    - **Cabeçalhos permitidos:** `Authorization`, `Content-Type`.
>    - Caso o front-end venha a rodar em outra porta local (como `3000`) ou em produção, basta incluir a respectiva URL no bean de configuração em `SecurityConfig`.

> [!TIP]
> 3. **Formatos de Erro Heterogêneos:**
>    O Back-End retorna erros de validação como JSON (`{"campo": "mensagem"}`), mas erros de entidade não encontrada ou conflitos como **texto puro** (`text/plain`, ex: `"Produto não encontrado"`). No seu cliente HTTP, trate `typeof error.response.data === 'string'` para capturar essas mensagens diretamente.
