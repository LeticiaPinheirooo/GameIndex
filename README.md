# GameIndex

Catálogo pessoal de jogos: cadastre o que jogou, quando, a nota, a plataforma, a categoria e se é favorito. Front-end em HTML/CSS/JS, back-end em Spring Boot + JdbcTemplate, banco H2.

<br>

---
<br>

## Tecnologias

- **Back-end:** Java, Spring Boot, JdbcTemplate
- **Banco:** H2 (populado por `schema.sql`)
- **Front-end:** HTML, CSS e JavaScript puro

## Estrutura

```
gameindex/
├── backend/     # API Spring Boot
├── frontend/    # index.html, css
└── README.md    # este arquivo
```
<br>

---
<br>

## Como rodar

- **Back-end:** abra a pasta `backend/` na IDE e rode a classe principal. A API sobe em `http://localhost:8080`.

- **Front-end:** veja [`front-end/README.md`](./front-end/README.md) - precisa do Live Server, não dá pra abrir com duplo clique.

<small>O front roda em `http://127.0.0.1:5500` e chama a API em `http://localhost:8080/jogos` via `fetch()`. O CORS já está liberado pra essa origem no `@CrossOrigin` do back-end.</small>

<br>

---
<br>

## Endpoints

Base: `http://localhost:8080/jogos`

### `GET /jogos`
Lista todos os jogos cadastrados.

**200 OK**
```json
[
  {
    "id": 100,
    "nome": "Katana Zero",
    "dataJogou": "2026-08-20",
    "nota": 10,
    "categoria": "Ação",
    "plataforma": "PC",
    "favorito": true
  }
]
```

<!-- ### `GET /jogos/procurar?nome={texto}`
Busca jogos pelo nome (parcial, sem diferenciar maiúscula/minúscula).

**200 OK** — mesmo formato acima, filtrado. Lista vazia `[]` se nada bater. -->

### `GET /jogos/plataformas`
Lista as plataformas do banco — usado pra popular o `<select>` dinamicamente.

**200 OK**
```json
[
  { "idPlat": 1, "nomePlat": "PC" },
  { "idPlat": 2, "nomePlat": "CONSOLE" }
]
```
**404 Not Found** — se não houver plataforma cadastrada.

### `POST /jogos`
Cadastra um jogo novo.

**Requisição**
```json
{
  "nome": "The Last of Us",
  "dataJogou": "2026-04-20",
  "nota": 10,
  "plataforma": "PC",
  "categoria": "Ação",
  "favorito": true
}
```

**201 Created** — devolve o jogo criado, com `id`.

**400 Bad Request** — quando falta algum campo obrigatório, `nota` fora de 0–10, `dataJogou` no futuro, ou `plataforma`/`categoria` não existem no banco.

<br>

---
<br>

## Funcionalidades

- Cadastrar jogo (nome, data, nota, plataforma, categoria, favorito)
- Listar jogos cadastrados
<!-- - Buscar jogo por nome -->
- Marcar como favorito ★

<br>

---
