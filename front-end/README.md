# GameIndex — Front-end

HTML, CSS e JavaScript puro. Consome a API via `fetch()`.

## Antes de rodar

O back-end precisa estar rodando em `http://localhost:8080`, senão o formulário não carrega as plataformas nem salva jogos.

---

## Por que Live Server

Abrir o `index.html` com duplo clique (`file:///...`) quebra o `fetch()` por causa de CORS. Precisa servir por um servidor local.

**Passo a passo (VS Code):**

1. Instale a extensão **Live Server**.
2. Clique com o botão direito em `index.html`.
3. Escolha **"Open with Live Server"**.
4. Abre em `http://127.0.0.1:5500` (essa porta precisa bater com o `@CrossOrigin` do back-end).

---

## Checagem rápida

Com tudo rodando, o campo **Plataforma** deve vir preenchido sozinho e a lista de jogos deve aparecer, se já houver algum cadastrado. Se nenhum dos dois aparecer, o back-end provavelmente não está no ar.
