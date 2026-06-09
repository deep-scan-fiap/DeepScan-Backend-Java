# DeepScan — Backend Java

API REST em Quarkus 3.8 / Java 17 que expõe o CRUD do domínio do projeto DeepScan (estações de monitoramento, leituras telemétricas, alertas de tsunami, espécies marinhas, avistamentos e zonas).

## Base URL

```
https://deepscan.labs-lcs-server.com/deepscan
```

Todas as rotas têm o prefixo `/deepscan`.

## Endpoints

### Estações de monitoramento

| Método | Path | Body (JSON) |
|---|---|---|
| `GET` | `/estacoes` | — |
| `GET` | `/estacoes/{id}` | — |
| `POST` | `/estacoes` | `{ "nomeEstacao": str, "latEstacao": float, "lonEstacao": float, "tipoEstacao": "BOIA" }` |
| `PUT` | `/estacoes/{id}` | idem ao POST |
| `DELETE` | `/estacoes/{id}` | — |

### Leituras telemétricas

| Método | Path | Body (JSON) |
|---|---|---|
| `GET` | `/leituras` | — |
| `GET` | `/leituras/{id}` | — |
| `GET` | `/leituras/estacao/{idEstacao}` | — |
| `POST` | `/leituras` | `{ "idEstacao": int, "horarioLeitura": "YYYY-MM-DDTHH:MM:SS", "sst": float, "waveHeight": float, "wavePeriod": float, "windSpeed": float, "windDirection": float, "earthquakeMagnitude": float, "focalDepth": float }` |
| `DELETE` | `/leituras/{id}` | — |

### Alertas

| Método | Path | Body (JSON) |
|---|---|---|
| `GET` | `/alertas` | — |
| `GET` | `/alertas/pendentes` | — |
| `PUT` | `/alertas/{id}/resolver` | — (marca como resolvido) |
| `DELETE` | `/alertas/{id}` | — |

### Espécies

| Método | Path | Body (JSON) |
|---|---|---|
| `GET` | `/especies` | — |
| `GET` | `/especies/{id}` | — |
| `POST` | `/especies` | `{ "ncEspecie": str, "npEspecie": str, "conservaEspecie": "CR"\|"EN"\|"VU"\|..., "habitatEspecie": str, "descEspecie": str }` |
| `PUT` | `/especies/{id}` | idem ao POST |
| `DELETE` | `/especies/{id}` | — |

`conservaEspecie` segue o padrão IUCN de classificação de conservação.

### Avistamentos

| Método | Path | Body (JSON) |
|---|---|---|
| `GET` | `/avistamentos` | — |
| `GET` | `/avistamentos/{id}` | — |
| `GET` | `/avistamentos/especie/{idEspecie}` | — |
| `POST` | `/avistamentos` | `{ "idEstacao": int, "idEspecie": int, "horarioAvista": "YYYY-MM-DDTHH:MM:SS", "quantAvista": int }` |
| `PUT` | `/avistamentos/{id}` | idem ao POST |
| `DELETE` | `/avistamentos/{id}` | — |

### Zonas

| Método | Path | Body (JSON) |
|---|---|---|
| `GET` | `/zonas` | — |
| `POST` | `/zonas` | `{ "nomeZona": str, "paisZona": str, "descZona": str }` |
| `DELETE` | `/zonas/{id}` | — |

## Convenções

- `Content-Type: application/json` em todos os `POST` e `PUT`.
- IDs (`idEstacao`, `idLeitura`, `idAlerta`, etc.) são gerados por sequence no Oracle — não envie nos `POST`.
- Datas viajam como ISO-8601 sem timezone (`YYYY-MM-DDTHH:MM:SS`).
- Erros retornam body `{"erro": "<mensagem>"}` com os status:
  - `400` — validação de entrada (ex.: quantidade ≤ 0).
  - `404` — recurso não encontrado.
  - `500` — erro interno (falha de DB ou exceção não esperada).

## CORS

Liberado para qualquer origem via `CorsFilter` (`Access-Control-Allow-Origin: *`).

## Stack

- Java 17, Quarkus 3.8.3 (resteasy-reactive + jackson)
- Oracle JDBC (ojdbc11) — DAO direto, sem JPA
- Empacotado como imagem Docker multi-stage (`Dockerfile` na raiz). Build e deploy via `.github/workflows/deploy.yml` em runner self-hosted.
