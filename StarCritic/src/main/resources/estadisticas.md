# Estadísticas — datos disponibles y gráficos propuestos

Inventario de qué columnas del modelo sirven para qué métrica, qué métodos ya
existen en `EstadisticasDB`, y qué tipo de gráfico (XChart) encaja con cada
dato.

Convenciones:

- **Personal** → consulta filtrada por `ID_usuario_registrado`.
- **General** → agregado sobre toda la plataforma.
- **Estado** → ✅ ya implementado, 🟡 datos en BD pero sin método aún.

---

## 1. Estadísticas de usuario (panel personal)

### 1.1 Resumen numérico (KPIs)
- **Datos:** reseñas, visitas (`SUM(num_visitas)`), listas, guardados distintos.
- **Origen:** `critica`, `contenido_usuario`, `lista_usuario`, `lista_contenido`.
- **Gráfico:** tarjetas KPI (sin chart) o `CategoryChart` de barras horizontales cortas.
- **Estado:** ✅ `resumenUsuario`.

### 1.2 Guardados por tipo de contenido
- **Datos:** nº de contenidos distintos en mis listas, agrupados por
  `tipo_contenido` (PELICULA / SERIE / VIDEOJUEGO).
- **Gráfico:** `PieChart` (donut) — pocas categorías, comparación de proporción.
- **Estado:** ✅ `contenidoEnListasPorTipo`.

### 1.3 Perfil de gusto por aspecto
- **Datos:** `AVG(critica.puntuacion)` agrupado por `aspecto.nombre` para
  mis reseñas.
- **Gráfico:** `RadarChart` (XChart 3.8+) — perfecto para “perfil multieje”.
  Alternativa: `CategoryChart` de barras horizontales ordenadas DESC.
- **Estado:** ✅ `mediaPorAspectoUsuario`.

### 1.4 Visitas por mes
- **Datos:** `SUM(num_visitas)` agrupado por `DATE_FORMAT(fecha_visita,'%Y-%m')`.
- **Gráfico:** `XYChart` de líneas (serie temporal).
- **Estado:** ✅ `visitasPorMesUsuario`.

### 1.5 Mi media vs. media global
- **Datos:** `AVG(puntuacion)` mío vs. global.
- **Gráfico:** `CategoryChart` de dos barras enfrentadas, o gauge.
- **Estado:** ✅ `mediaUsuarioVsGlobal`.

### 1.6 Distribución de mis puntuaciones 🟡
- **Datos:** histograma de `critica.puntuacion` en tramos (0-19, 20-39…).
- **Gráfico:** `CategoryChart` de barras (histograma).
- **Por qué:** revela si soy un crítico “duro”, “generoso” o equilibrado.

### 1.7 Reseñas por tipo de contenido 🟡
- **Datos:** `COUNT(*)` de mis críticas, agrupado por `tipo_contenido`
  uniendo `critica_audiovisual` + `critica_videojuego`.
- **Gráfico:** `PieChart` donut.

### 1.8 Top contenidos más revisitados (personal) 🟡
- **Datos:** `contenido_usuario.num_visitas DESC` para mi usuario, top 10.
- **Gráfico:** `CategoryChart` de barras horizontales con título de contenido.

### 1.9 Géneros/etiquetas que más consumo 🟡
- **Datos:** `contenido_usuario` ⨝ `contenido_etiqueta` ⨝ `etiqueta_editorial`,
  contado por etiqueta.
- **Gráfico:** `CategoryChart` de barras horizontales (top 10).

### 1.10 Reparto de mis guardados por origen 🟡
- **Datos:** `COUNT(*)` agrupado por `contenido.origen` (OMDB/RAWG/LOCAL).
- **Gráfico:** `PieChart` donut.

### 1.11 Días de actividad / racha 🟡
- **Datos:** `COUNT(DISTINCT fecha_visita)` y huecos entre fechas.
- **Gráfico:** `XYChart` de pasos o heatmap (XChart no lo tiene nativo; usar
  matriz de barras por semana/día).

### 1.12 Longitud media de mis críticas 🟡
- **Datos:** `AVG(LENGTH(descripcion))` agrupado por mes o por tipo.
- **Gráfico:** KPI suelto o `CategoryChart`.

### 1.13 Estado de mi certificación (si soy crítico) 🟡
- **Datos:** `critico.estado_certificacion` para mi usuario.
- **Gráfico:** etiqueta/badge (no chart) — útil mostrarlo en el panel.

---

## 2. Estadísticas generales (panel admin / home)

### 2.1 Reparto del catálogo por tipo
- **Datos:** `COUNT(*) FROM contenido GROUP BY tipo_contenido`.
- **Gráfico:** `PieChart` donut.
- **Estado:** ✅ `catalogoPorTipo`.

### 2.2 Reparto del catálogo por origen
- **Datos:** `COUNT(*) FROM contenido GROUP BY origen` (OMDB/RAWG/LOCAL).
- **Gráfico:** `PieChart` donut o barras.
- **Estado:** ✅ `catalogoPorOrigen`.

### 2.3 Top contenidos más visitados
- **Datos:** `SUM(num_visitas)` agrupado por contenido, ORDER BY DESC LIMIT N.
- **Gráfico:** `CategoryChart` de barras horizontales (top 10).
- **Estado:** ✅ `topContenidoMasVisitado`.

### 2.4 Altas de usuarios por mes
- **Datos:** `COUNT(*) FROM usuario_registrado GROUP BY mes`.
- **Gráfico:** `XYChart` de líneas (crecimiento) + opcional acumulado.
- **Estado:** ✅ `altasUsuariosPorMes`.

### 2.5 Histograma global de puntuaciones
- **Datos:** tramos de 20 sobre `critica.puntuacion` (rango 0–100).
- **Gráfico:** `CategoryChart` de barras (histograma).
- **Estado:** ✅ `distribucionPuntuaciones`.

### 2.6 Estado de certificaciones de crítico
- **Datos:** `COUNT(*) FROM critico GROUP BY estado_certificacion`.
- **Gráfico:** `PieChart` o barras apiladas.
- **Estado:** ✅ `estadoCertificaciones`.

### 2.7 Top contenidos mejor / peor puntuados 🟡
- **Datos:** `AVG(critica.puntuacion)` por contenido, con
  `HAVING COUNT(*) >= N` (filtrar contenidos con muy pocas reseñas).
- **Gráfico:** dos `CategoryChart` de barras horizontales (top 10 alto / bajo).

### 2.8 Media de puntuación por aspecto (global) 🟡
- **Datos:** `AVG(puntuacion)` agrupado por `aspecto.nombre`.
- **Gráfico:** `RadarChart` global, o barras horizontales.
- **Por qué:** identifica qué dimensiones puntúa mejor o peor la comunidad
  (p.ej. “historia” suele puntuarse más alto que “gráficos”).

### 2.9 Reseñas por aspecto 🟡
- **Datos:** `COUNT(*)` agrupado por aspecto.
- **Gráfico:** `CategoryChart` de barras.
- **Por qué:** qué dimensiones interesan más a los usuarios al opinar.

### 2.10 Visitas totales por mes 🟡
- **Datos:** `SUM(num_visitas)` agrupado por
  `DATE_FORMAT(fecha_visita,'%Y-%m')`.
- **Gráfico:** `XYChart` de líneas — pulso global de la plataforma.

### 2.11 Reseñas publicadas por mes 🟡
- **Datos:** `COUNT(*)` agrupado por mes (requiere columna fecha en `critica`;
  verificar si existe — el POJO no la expone).
- **Gráfico:** `XYChart` de líneas o barras.

### 2.12 Altas de contenido por mes 🟡
- **Datos:** `COUNT(*) FROM contenido GROUP BY DATE_FORMAT(fecha,'%Y-%m')`.
- **Gráfico:** `XYChart` de líneas, separable por origen o tipo (stacked).

### 2.13 Ranking de usuarios más activos 🟡
- **Datos:** top usuarios por nº de reseñas, nº de visitas o nº de listas.
- **Gráfico:** `CategoryChart` de barras horizontales (top 10).

### 2.14 Reparto de roles y baneos 🟡
- **Datos:** `COUNT(*) FROM usuario_registrado GROUP BY rol`,
  `GROUP BY baneado`.
- **Gráfico:** `PieChart` doble o barras apiladas.

### 2.15 Top etiquetas editoriales 🟡
- **Datos:** `COUNT(*)` sobre `contenido_etiqueta` agrupado por etiqueta;
  también su versión “más visitada” cruzando con `contenido_usuario`.
- **Gráfico:** `CategoryChart` de barras horizontales.

### 2.16 Impacto del flag `destacado` 🟡
- **Datos:** `AVG(num_visitas)` para `destacado=1` vs `destacado=0`.
- **Gráfico:** `CategoryChart` de 2 barras (comparativa) o KPI doble.
- **Por qué:** mide si la curación editorial empuja tráfico.

### 2.17 Contenido oculto vs visible 🟡
- **Datos:** `COUNT(*) GROUP BY oculto`.
- **Gráfico:** `PieChart` pequeño / KPI.

### 2.18 Contenidos sin reseñas o sin visitas 🟡
- **Datos:** `LEFT JOIN` para detectar huérfanos del catálogo.
- **Gráfico:** KPI numérico + lista; no necesita chart.

### 2.19 Nombres de lista más populares 🟡
- **Datos:** `COUNT(*) FROM lista_usuario GROUP BY nombre_lista`.
- **Gráfico:** `CategoryChart` de barras horizontales — revela qué taxonomía
  espontánea usan los usuarios (“Favoritos”, “Pendientes”, “Vistos/Jugados”…).

### 2.20 Listas creadas por mes 🟡
- **Datos:** `COUNT(*) FROM lista_usuario GROUP BY mes`.
- **Gráfico:** `XYChart` de líneas.

---

## 3. Mapa rápido columna → métrica

| Columna | Métricas que habilita |
|---|---|
| `contenido_usuario.num_visitas`, `fecha_visita` | Volumen, engagement, series temporales, top contenidos |
| `critica.puntuacion` | Calidad percibida, histogramas, medias, perfiles |
| `critica_*.ID_aspecto` | Perfil de gusto, radar, foco crítico |
| `usuario_registrado.fecha_creacion` | Crecimiento de la base de usuarios |
| `usuario_registrado.rol`, `baneado` | Moderación y composición de la comunidad |
| `critico.estado_certificacion` | Estado del flujo de certificación |
| `contenido.fecha`, `origen`, `tipo_contenido` | Crecimiento y composición del catálogo |
| `contenido.destacado`, `oculto` | Efectos de curación editorial |
| `lista_usuario.nombre_lista`, `fecha_creacion` | Taxonomía espontánea, actividad de listas |
| `lista_contenido.ID_contenido` | Curación social — qué se guarda |
| `etiqueta_editorial.nombre` (+ `contenido_etiqueta`) | Categorización editorial, géneros |

---

## 4. Tipos de gráfico XChart recomendados por caso

- **`PieChart` (donut):** repartos con pocas categorías (tipo, origen, rol, estado).
- **`CategoryChart` barras horizontales:** rankings/top N, histogramas, comparativas.
- **`XYChart` líneas:** series temporales mensuales (altas, visitas, reseñas).
- **`XYChart` áreas apiladas:** crecimiento por categoría (catálogo por origen mes a mes).
- **`RadarChart`:** perfil multieje por aspecto (personal y global).
- **`BubbleChart`:** correlación nº reseñas × media de puntuación por contenido.
