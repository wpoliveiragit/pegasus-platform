# pegasus-platform
## Tarefas
- Pegasus-api-product
  - Remover o endpoint `up` quando a conexão entre o back e o front estiver em 100%

# Mapa Mental da estrutura

```text
enterprise-platform/
│
├─ pom.xml  (POM PAI)
├─ docker-compose.yml
├─ doc
│
├─ fronts/
│  ├─ front-a/
│  │  ├─ pom.xml
│  │  ├─ Dockerfile
│  │  └─ src/...
│  │
│  └─ front-b/
│     ├─ pom.xml
│     ├─ Dockerfile
│     └─ src/...
│
├─ gateway/
│  ├─ pom.xml
│  ├─ Dockerfile
│  └─ src/...
│
├─ services/
│  ├─ service-a/
│  │  ├─ pom.xml
│  │  ├─ Dockerfile
│  │  └─ src/...
│  │
│  └─ service-b/
│     ├─ pom.xml
│     ├─ Dockerfile
│     └─ src/...
```

# Adição de novos recursos

## back-end

### Ajustes

## front-end

### Ajustes

# Comandos

Todos os comandos devem ser dados no raiz da plataforma

```shell
  # - Limpa todos os targets
  # - atualiza toda plataforma
  # - builda toda plataforma
  # - atualiza as versões no repositório (neste caso `local`)
  mvn clean install -U
```


```shell
  # derruba a plataforma no docker
  docker compose down`
```

```shell
  # - Limpa o cache do docker
  # - Resgata todas as versões dos projetos da plataforma (já devidamente compilados)
  # - Rebuilda a plataforma no docker
  docker compose build --no-cache
```

```shell
  # - Remove todas os projetos que não existem na plataforma
  # - Sobe a plataforma no docker
  docker compose up -d --remove-orphans
```
