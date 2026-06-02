# Atributos disponibles en las APIs usadas por DAM-Proyect

Documento generado a partir de llamadas reales hechas mediante las clases
`OMDbRepository` y `RAWGRepository` del paquete `service`.
Ejemplos consultados:

- Películas: **"Matrix" / "The Matrix"** (OMDb)
- Series: **"Breaking Bad"** (OMDb)
- Videojuegos: **"Zelda"** (RAWG)

---

## 1. Películas (OMDb — `http://www.omdbapi.com/`)

OMDb ofrece dos tipos de respuesta distintos según el endpoint usado:

### 1.1 Búsqueda (`?s=<texto>&type=movie`)  →  `OMDbRepository.getFilms()`

La respuesta contiene un array `Search` con objetos reducidos y metadatos
de paginación:

| Atributo       | Tipo    | Descripción                                   |
|----------------|---------|-----------------------------------------------|
| `Title`        | string  | Título de la película                         |
| `Year`         | string  | Año de estreno                                |
| `imdbID`       | string  | Identificador único de IMDb (p.ej. tt0133093) |
| `Type`         | string  | Siempre `"movie"` para esta búsqueda          |
| `Poster`       | string  | URL del póster                                |
| `totalResults` | string  | Nº total de resultados encontrados            |
| `Response`     | string  | `"True"` / `"False"`                          |

### 1.2 Detalle (`?t=<titulo>&type=movie&plot=full`)  →  `OMDbRepository.getFilmDetail()`

Devuelve la ficha completa de una película:

| Atributo      | Tipo              | Descripción                                              |
|---------------|-------------------|----------------------------------------------------------|
| `Title`       | string            | Título                                                   |
| `Year`        | string            | Año                                                      |
| `Rated`       | string            | Clasificación por edades (G, PG, R, …)                   |
| `Released`    | string            | Fecha de estreno (`dd MMM yyyy`)                         |
| `Runtime`     | string            | Duración (`"136 min"`)                                   |
| `Genre`       | string            | Géneros separados por comas                              |
| `Director`    | string            | Director(es)                                             |
| `Writer`      | string            | Guionista(s)                                             |
| `Actors`      | string            | Reparto principal                                        |
| `Plot`        | string            | Sinopsis (completa si `plot=full`)                       |
| `Language`    | string            | Idiomas                                                  |
| `Country`     | string            | Países de producción                                     |
| `Awards`      | string            | Premios y nominaciones                                   |
| `Poster`      | string            | URL del póster                                           |
| `Ratings`     | array de objetos  | Cada item: `{ "Source": string, "Value": string }`       |
| `Metascore`   | string            | Puntuación Metacritic                                    |
| `imdbRating`  | string            | Nota IMDb (0–10)                                         |
| `imdbVotes`   | string            | Nº de votos en IMDb                                      |
| `imdbID`      | string            | Identificador IMDb                                       |
| `Type`        | string            | `"movie"`                                                |
| `DVD`         | string            | Fecha de salida en DVD (puede ser `"N/A"`)               |
| `BoxOffice`   | string            | Recaudación en taquilla                                  |
| `Production`  | string            | Productora                                               |
| `Website`     | string            | Web oficial                                              |
| `Response`    | string            | `"True"` / `"False"`                                     |

---

## 2. Series (OMDb — `http://www.omdbapi.com/`)

### 2.1 Búsqueda (`?s=<texto>&type=series`)  →  `OMDbRepository.getSeries()`

Los campos son **idénticos** a la búsqueda de películas, salvo que
`Type` vale `"series"`:

`Title`, `Year`, `imdbID`, `Type` (`"series"`), `Poster`, `totalResults`, `Response`.

> Nota: el campo `Year` puede contener un rango (p. ej. `"2008–2013"`) si
> la serie ya ha terminado, o `"2025–"` si sigue en emisión.

### 2.2 Detalle (`?t=<titulo>&type=series&plot=full`)  →  `OMDbRepository.getSeriesDetail()`

Incluye todos los campos de la ficha de película, con algunas diferencias:

| Atributo        | Tipo             | Descripción                                            |
|-----------------|------------------|--------------------------------------------------------|
| `Title`         | string           | Título                                                 |
| `Year`          | string           | Rango de años de emisión                               |
| `Rated`         | string           | Clasificación (p.ej. `TV-MA`)                          |
| `Released`      | string           | Fecha de estreno del piloto                            |
| `Runtime`       | string           | Duración media por episodio                            |
| `Genre`         | string           | Géneros                                                |
| `Director`      | string           | Puede ser `"N/A"` (series con múltiples directores)    |
| `Writer`        | string           | Creador / guionistas                                   |
| `Actors`        | string           | Reparto principal                                      |
| `Plot`          | string           | Sinopsis                                               |
| `Language`      | string           | Idiomas                                                |
| `Country`       | string           | Países                                                 |
| `Awards`        | string           | Premios                                                |
| `Poster`        | string           | URL del póster                                         |
| `Ratings`       | array de objetos | `{ "Source", "Value" }`                                |
| `Metascore`     | string           | Puede ser `"N/A"` en series                            |
| `imdbRating`    | string           | Nota IMDb                                              |
| `imdbVotes`     | string           | Nº de votos                                            |
| `imdbID`        | string           | Identificador IMDb                                     |
| `Type`          | string           | `"series"`                                             |
| `totalSeasons`  | string           | **Exclusivo de series** — nº total de temporadas       |
| `Response`      | string           | `"True"` / `"False"`                                   |

> A diferencia de las películas, las series **no** traen `DVD`, `BoxOffice`,
> `Production` ni `Website`, y añaden `totalSeasons`.

---

## 3. Videojuegos (RAWG — `https://api.rawg.io/api/games`)

Endpoint usado: `?key=<api_key>&search=<texto>`  →  `RAWGRepository.getGames()`

La respuesta tiene una estructura paginada:

| Atributo         | Tipo    | Descripción                                      |
|------------------|---------|--------------------------------------------------|
| `count`          | int     | Nº total de resultados                           |
| `next`           | string  | URL de la página siguiente (puede ser `null`)    |
| `previous`       | string  | URL de la página anterior (puede ser `null`)     |
| `results`        | array   | Lista de juegos (ver tabla inferior)             |
| `user_platforms` | bool    | Indicador de filtrado por plataformas de usuario |

### 3.1 Objeto juego (`results[i]`)

| Atributo             | Tipo             | Descripción                                                                    |
|----------------------|------------------|--------------------------------------------------------------------------------|
| `id`                 | int              | Identificador único del juego en RAWG                                          |
| `slug`               | string           | Identificador legible (`"the-legend-of-zelda-breath-of-the-wild"`)             |
| `name`               | string           | Nombre del juego                                                               |
| `released`           | string           | Fecha de lanzamiento (`yyyy-MM-dd`)                                            | 
| `tba`                | bool             | *To Be Announced* (sin fecha confirmada)                                       |
| `background_image`   | string           | URL de la imagen de portada                                                    |
| `rating`             | number           | Nota media de los usuarios (0–5)                                               |
| `rating_top`         | int              | Escala máxima usada por el juego                                               |
| `ratings`            | array de objetos | Distribución de valoraciones (`id`, `title`, `count`, `percent`)               |
| `ratings_count`      | int              | Nº total de valoraciones                                                       |
| `reviews_text_count` | int              | Nº de reseñas con texto                                                        |
| `reviews_count`      | int              | Nº total de reseñas                                                            |
| `metacritic`         | int              | Puntuación Metacritic (puede ser `null`)                                       |
| `playtime`           | int              | Duración media estimada en horas                                               |
| `suggestions_count`  | int              | Nº de juegos sugeridos/similares                                               |
| `added`              | int              | Veces que ha sido añadido a colecciones                                        |
| `added_by_status`    | object           | Desglose: `yet`, `owned`, `beaten`, `toplay`, `dropped`, `playing`             |
| `updated`            | string           | Fecha de última actualización (ISO 8601)                                       |
| `score`              | string           | Score de relevancia de la búsqueda                                             |
| `clip`               | object / null    | Video clip (`clip`, `clips`, `video`, `preview` cuando no es null)             |
| `tags`               | array de objetos | Etiquetas: `id`, `name`, `slug`, `language`, `games_count`, `image_background` |
| `esrb_rating`        | object / null    | Clasificación ESRB (`id`, `name`, `slug`) cuando no es null                    |
| `user_game`          | any / null       | Datos del usuario autenticado (normalmente `null`)                             |
| `community_rating`   | int              | Valoración global de la comunidad                                              |
| `saturated_color`    | string           | Color principal en HEX (sin `#`)                                               |
| `dominant_color`     | string           | Color dominante en HEX (sin `#`)                                               |
| `short_screenshots`  | array de objetos | Capturas: `{ id, image }`                                                      |
| `platforms`          | array de objetos | Cada item: `{ platform: { id, name, slug }, ... }`                             |
| `parent_platforms`   | array de objetos | Agrupación de plataformas padre: `{ platform: { id, name, slug } }`            |
| `stores`             | array de objetos | Tiendas: `{ store: { id, name, slug } }`                                       |
| `genres`             | array de objetos | Géneros: `{ id, name, slug }`                                                  |

### 3.2 Detalle (`GET /api/games/{id}`)  →  `RAWGRepository.getGameDetails()`

La ficha de detalle añade campos que **no** aparecen en la búsqueda. El más
relevante para la aplicación es la sinopsis del juego:

| Atributo            | Tipo   | Descripción                                                       |
|---------------------|--------|-------------------------------------------------------------------|
| `description`       | string | Sinopsis del juego en **HTML** (con etiquetas `<p>`)              |
| `description_raw`   | string | Sinopsis del juego en **texto plano** (sin etiquetas)            |

> Equivale al `Plot` de OMDb: se guarda en la columna `sinopsis` de `contenido`
> y se muestra en la ficha de detalle. Se prefiere `description_raw` (texto
> plano) frente a `description` (HTML). Puede superar los 3000 caracteres, por
> lo que `sinopsis` se define como `TEXT`.

> ⚠️ **Limitación importante — creadores/desarrolladores no disponibles.**
> El endpoint de búsqueda `?search=` **no** devuelve los campos `developers`,
> `publishers`, `creators` ni equivalentes. Verificado mediante una llamada
> real a `RAWGRepository.getGames("Zelda")`: ninguno de los objetos de
> `results[]` contiene información sobre quién creó o desarrolló el juego.
>
> Para obtener esos datos hay que usar otros endpoints de RAWG (no
> implementados actualmente en `RAWGRepository`):
> - `GET /api/games/{id}` — la ficha de detalle incluye `developers` y
>   `publishers` (arrays de `{ id, name, slug }`).
> - `GET /api/games/{id}/development-team` — lista de personas individuales
>   acreditadas en el juego (`creators`), con `id`, `name`, `slug`,
>   `image`, `image_background` y `positions`.

---

## Notas operativas

- Las API keys se cargan desde `resources/config.properties` a través de
  `BaseRepository.getApiKey()` (claves: `OMDB_API_KEY`, `RAWG_API_KEY`).
- Todas las peticiones se realizan por HTTP GET mediante
  `BaseRepository.executeGet(URI)`.
- OMDb devuelve los campos ausentes como la cadena literal `"N/A"`, no como
  `null`. RAWG sí usa `null` en campos sin datos.
- En búsquedas OMDb la respuesta está limitada a 10 resultados por página;
  para paginar se usa el parámetro `&page=<n>`.
