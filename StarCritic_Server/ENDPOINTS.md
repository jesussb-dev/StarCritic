# Endpoints de la API

Todas las rutas cuelgan de la base `/api`. Total: **16 controladores**, ~88 endpoints.

## Índice

- [Aspectos](#aspectos--apiaspectos)
- [Contenidos](#contenidos--apicontenidos)
- [Contenidos Audiovisuales](#contenidos-audiovisuales--apicontenidos-audiovisuales)
- [Videojuegos](#videojuegos--apivideojuegos)
- [Usuarios](#usuarios--apiusuarios)
- [Críticos](#críticos--apicriticos)
- [Críticas](#críticas--apicriticas)
- [Críticas Audiovisuales](#críticas-audiovisuales--apicriticas-audiovisuales)
- [Críticas Videojuegos](#críticas-videojuegos--apicriticas-videojuegos)
- [Etiquetas Editoriales](#etiquetas-editoriales--apietiquetas)
- [Mensajes](#mensajes--apimensajes)
- [Listas de Usuario](#listas-de-usuario--apilistas-usuario)
- [Listas de Contenido](#listas-de-contenido--apilistas-contenido)
- [Contenido-Usuario](#contenido-usuario--apicontenido-usuario)
- [Recomendaciones](#recomendaciones--apirecomendaciones)
- [Estadísticas](#estadísticas--apiestadisticas)

---

## Aspectos — `/api/aspectos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/aspectos` | Listar todos |
| GET | `/api/aspectos/audiovisual` | Aspectos de audiovisual |
| GET | `/api/aspectos/videojuego` | Aspectos de videojuego |
| GET | `/api/aspectos/{id}` | Obtener por id |
| POST | `/api/aspectos` | Crear |
| DELETE | `/api/aspectos/{id}` | Eliminar |

## Contenidos — `/api/contenidos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/contenidos` | Listar todos |
| GET | `/api/contenidos/{id}` | Obtener por id |
| POST | `/api/contenidos` | Crear |
| DELETE | `/api/contenidos/{id}` | Eliminar |
| GET | `/api/contenidos/{id}/media-audiovisual` | Media de aspectos (audiovisual) |
| GET | `/api/contenidos/{id}/media-videojuego` | Media de aspectos (videojuego) |
| PATCH | `/api/contenidos/{id}/oculto` | Marcar oculto |
| PATCH | `/api/contenidos/{id}/destacado` | Marcar destacado |

## Contenidos Audiovisuales — `/api/contenidos-audiovisuales`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/contenidos-audiovisuales` | Listar todos |
| GET | `/api/contenidos-audiovisuales/{id}` | Obtener por id |
| GET | `/api/contenidos-audiovisuales/omdb/{idOmdb}` | Buscar por id de OMDb |
| GET | `/api/contenidos-audiovisuales/omdb/{idOmdb}/existe` | ¿Existe por OMDb? |
| POST | `/api/contenidos-audiovisuales` | Crear |
| DELETE | `/api/contenidos-audiovisuales/{id}` | Eliminar |

## Videojuegos — `/api/videojuegos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/videojuegos` | Listar todos |
| GET | `/api/videojuegos/{id}` | Obtener por id |
| GET | `/api/videojuegos/rawg/{idRawg}` | Buscar por id de RAWG |
| GET | `/api/videojuegos/rawg/{idRawg}/existe` | ¿Existe por RAWG? |
| POST | `/api/videojuegos` | Crear |
| DELETE | `/api/videojuegos/{id}` | Eliminar |

## Usuarios — `/api/usuarios`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/usuarios` | Listar todos |
| GET | `/api/usuarios/por-nombre?nombreUsuario=` | Buscar por nombre |
| GET | `/api/usuarios/por-correo?correo=` | Buscar por correo |
| GET | `/api/usuarios/existe?nombreUsuario=` | ¿Existe nombre? |
| GET | `/api/usuarios/{id}` | Obtener por id |
| POST | `/api/usuarios` | Crear |
| DELETE | `/api/usuarios/{id}` | Eliminar |

## Críticos — `/api/criticos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/criticos` | Listar todos |
| GET | `/api/criticos/pendientes` | Críticos pendientes |
| GET | `/api/criticos/estado/{estado}` | Filtrar por estado |
| GET | `/api/criticos/{id}` | Obtener por id |
| POST | `/api/criticos` | Crear |
| DELETE | `/api/criticos/{id}` | Eliminar |

## Críticas — `/api/criticas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/criticas` | Listar todas |
| GET | `/api/criticas/{id}` | Obtener por id |
| POST | `/api/criticas` | Crear |
| DELETE | `/api/criticas/{id}` | Eliminar |
| GET | `/api/criticas/audiovisual/aspecto/{idAspecto}/contenido/{idContenido}` | Por aspecto + contenido |
| GET | `/api/criticas/videojuego/aspecto/{idAspecto}/videojuego/{idVideojuego}` | Por aspecto + videojuego |
| GET | `/api/criticas/audiovisual/aspecto/{idAspecto}/usuario/{idUsuario}` | Por aspecto + usuario (av) |
| GET | `/api/criticas/videojuego/aspecto/{idAspecto}/usuario/{idUsuario}` | Por aspecto + usuario (vj) |
| GET | `/api/criticas/{idCritica}/es-de-usuario/{idUsuario}` | ¿Crítica es de ese usuario? |

## Críticas Audiovisuales — `/api/criticas-audiovisuales`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/criticas-audiovisuales` | Listar todas |
| GET | `/api/criticas-audiovisuales/{id}` | Obtener por id |
| POST | `/api/criticas-audiovisuales` | Crear |
| DELETE | `/api/criticas-audiovisuales/{id}` | Eliminar |

## Críticas Videojuegos — `/api/criticas-videojuegos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/criticas-videojuegos` | Listar todas |
| GET | `/api/criticas-videojuegos/{id}` | Obtener por id |
| POST | `/api/criticas-videojuegos` | Crear |
| DELETE | `/api/criticas-videojuegos/{id}` | Eliminar |

## Etiquetas Editoriales — `/api/etiquetas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/etiquetas` | Listar todas |
| GET | `/api/etiquetas/contenido/{idContenido}` | Etiquetas de un contenido |
| GET | `/api/etiquetas/{id}` | Obtener por id |
| POST | `/api/etiquetas` | Crear |
| DELETE | `/api/etiquetas/{id}` | Eliminar |
| POST | `/api/etiquetas/contenido/{idContenido}/etiqueta/{idEtiqueta}` | Asignar etiqueta a contenido |
| DELETE | `/api/etiquetas/contenido/{idContenido}/etiqueta/{idEtiqueta}` | Quitar etiqueta de contenido |

## Mensajes — `/api/mensajes`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/mensajes` | Listar todos |
| GET | `/api/mensajes/destinatario/{idDestinatario}` | Mensajes de un destinatario |
| GET | `/api/mensajes/{id}` | Obtener por id |
| POST | `/api/mensajes` | Crear |
| DELETE | `/api/mensajes/{id}` | Eliminar |

## Listas de Usuario — `/api/listas-usuario`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/listas-usuario` | Listar todas |
| GET | `/api/listas-usuario/usuario/{idUsuario}` | Listas de un usuario |
| POST | `/api/listas-usuario` | Crear |
| DELETE | `/api/listas-usuario/usuario/{idUsuario}/lista/{nombreLista}` | Eliminar lista |

## Listas de Contenido — `/api/listas-contenido`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/listas-contenido` | Listar todas |
| POST | `/api/listas-contenido` | Añadir contenido a lista |
| GET | `/api/listas-contenido/usuario/{idUsuario}/lista/{nombreLista}` | Contenido de una lista |
| GET | `/api/listas-contenido/usuario/{idUsuario}/lista/{nombreLista}/contenido/{idContenido}/existe` | ¿Está el contenido en la lista? |
| DELETE | `/api/listas-contenido/usuario/{idUsuario}/lista/{nombreLista}/contenido/{idContenido}` | Quitar de la lista |

## Contenido-Usuario — `/api/contenido-usuario`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/contenido-usuario` | Listar todos |
| GET | `/api/contenido-usuario/{idUsuario}/{idContenido}` | Obtener relación |
| POST | `/api/contenido-usuario` | Crear |
| DELETE | `/api/contenido-usuario/{idUsuario}/{idContenido}` | Eliminar |
| POST | `/api/contenido-usuario/{idUsuario}/{idContenido}/visita` | Registrar visita |

## Recomendaciones — `/api/recomendaciones`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/recomendaciones?tipo=&idUsuario=&limite=10` | Recomendaciones (`tipo` obligatorio; `idUsuario` opcional; `limite` por defecto 10) |

## Estadísticas — `/api/estadisticas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/estadisticas/usuario/{idUsuario}/contenido-en-listas` | Contenido en listas del usuario |
| GET | `/api/estadisticas/usuario/{idUsuario}/media-por-aspecto` | Media por aspecto |
| GET | `/api/estadisticas/usuario/{idUsuario}/visitas-por-mes` | Visitas por mes |
| GET | `/api/estadisticas/usuario/{idUsuario}/resumen` | Resumen del usuario |
| GET | `/api/estadisticas/usuario/{idUsuario}/media-vs-global` | Media del usuario vs global |
| GET | `/api/estadisticas/top-visitado?limite=10` | Top más visitados |
| GET | `/api/estadisticas/catalogo-por-tipo` | Catálogo por tipo |
| GET | `/api/estadisticas/catalogo-por-origen` | Catálogo por origen |
| GET | `/api/estadisticas/altas-por-mes` | Altas por mes |
| GET | `/api/estadisticas/distribucion-puntuaciones` | Distribución de puntuaciones |
| GET | `/api/estadisticas/estado-certificaciones` | Estado de certificaciones |
