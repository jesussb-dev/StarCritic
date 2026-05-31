# Manual de instalación — Cliente StarCritic

**Aplicación:** StarCritic (cliente de escritorio)
**Tecnología:** Java 21 + Swing (FlatLaf)
**Versión del documento:** 1.0
**Dirigido a:** instaladores y usuarios técnicos que vayan a poner en marcha el cliente en un equipo nuevo.

---

## 1. Introducción

StarCritic es una aplicación de escritorio (Java Swing) para la gestión y crítica de
contenidos multimedia (películas, series y videojuegos).

El cliente **no accede a ninguna base de datos directamente**: toda la información se obtiene
y se modifica a través de la **API REST del servidor StarCritic** (`StarCritic_Server`),
mediante **HTTPS** con un certificado propio. Por tanto, para que el cliente funcione es
imprescindible que el **servidor esté en marcha y sea accesible** desde el equipo.

Este manual describe **todo lo que hay que realizar en el equipo cliente** para dejar la
aplicación instalada y funcionando.

---

## 2. Requisitos previos

### 2.1. Hardware mínimo recomendado

| Recurso | Mínimo |
|---------|--------|
| CPU | Doble núcleo 2 GHz |
| RAM | 4 GB (8 GB recomendado) |
| Disco | 500 MB libres |
| Pantalla | 1366 × 768 o superior |
| Red | Acceso al servidor StarCritic por HTTPS (puerto 8443) |

### 2.2. Software necesario

| Software | Versión | Necesario para |
|----------|---------|----------------|
| **Java (JRE o JDK)** | **21** | Ejecutar la aplicación |
| **JDK 21** | 21 | *Solo* si se va a compilar desde el código fuente |
| **Apache Maven** | 3.9+ | *Solo* para compilar (incluido en NetBeans) |
| **NetBeans IDE** *(opcional)* | 21+ | Entorno cómodo para compilar/ejecutar |
| **Git** *(opcional)* | Cualquiera | Para clonar el repositorio |

> **Importante:** el proyecto está fijado a **Java 21** (`maven.compiler.release = 21`). Para
> *ejecutar* la aplicación basta con un **JRE 21**; para *compilarla* hace falta el **JDK 21**.
> No es necesario instalar MySQL ni ninguna base de datos en el equipo cliente.

### 2.3. Comprobar la versión de Java

```bash
java -version     # debe indicar versión 21
```

Si `java` apunta a otra versión, ajuste `JAVA_HOME` para que apunte a un Java 21.

---

## 3. Formas de instalar el cliente

Hay dos caminos. Elija **uno**:

- **Opción A — Ejecutable ya compilado (recomendado para usuarios finales):**
  se distribuye un único JAR autocontenido (*fat jar*) que incluye todas las dependencias.
- **Opción B — Compilar desde el código fuente** (para desarrolladores o cuando no se
  dispone del JAR).

En ambos casos hay que **revisar la configuración** (sección 5) y asegurar la **conexión con
el servidor** (sección 6).

---

## 4. Obtener el software

### Opción A — JAR ya compilado

Copie al equipo el archivo:

```
StarCritic-1.0-SNAPSHOT-all.jar
```

Es un *fat jar* generado con `maven-shade-plugin` que **ya incluye dentro** todas las
dependencias y los recursos del programa (entre ellos `config.properties`, `truststore.p12`,
las imágenes y la plantilla de informes `Example.jrxml`).

> ⚠️ Como `config.properties` y `truststore.p12` van **empaquetados dentro del JAR**, si hay
> que cambiar la dirección del servidor o el certificado es necesario **recompilar** (Opción B)
> con los valores correctos. Por eso, para una instalación contra un servidor distinto al de
> fábrica, lo habitual es usar la Opción B.

### Opción B — Código fuente

Clone el repositorio o copie la carpeta del proyecto:

```bash
git clone <URL-del-repositorio>
cd NetBeansProjects/StarCritic
```

La carpeta del cliente es **`StarCritic`**. Estructura relevante:

```
StarCritic/
├── pom.xml
└── src/main/resources/
    ├── config.properties        # configuración: URL del servidor, claves de API, truststore
    ├── truststore.p12           # almacén de confianza con el certificado del servidor
    ├── Example.jrxml            # plantilla de informes (JasperReports)
    ├── img/                     # iconos e imágenes de la interfaz
    └── docs/                    # documentación y scripts (uso del servidor, no del cliente)
```

---

## 5. Configuración del cliente (`config.properties`)

Toda la configuración del cliente está en **`src/main/resources/config.properties`**. Este
archivo se empaqueta dentro del JAR (se lee del *classpath*). Su contenido es:

```properties
OMDB_API_KEY=xxxxxxxx
RAWG_API_KEY=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# URL base de la API REST del servidor StarCritic_Server
API_BASE_URL=https://172.27.250.6:8443/api

# Truststore (PKCS12) con el certificado del servidor. Es el NOMBRE del recurso
# dentro del classpath (carpeta resources), NO una ruta del sistema de ficheros.
TRUSTSTORE_PATH=truststore.p12
TRUSTSTORE_PASSWORD=changeit
```

| Propiedad | Descripción |
|-----------|-------------|
| `API_BASE_URL` | URL base de la API del servidor. **Debe apuntar a su servidor** (host/IP y puerto, normalmente `:8443`, terminando en `/api`). |
| `TRUSTSTORE_PATH` | Nombre del truststore dentro del JAR (por defecto `truststore.p12`). No lo cambie salvo que renombre el recurso. |
| `TRUSTSTORE_PASSWORD` | Contraseña del truststore (por defecto `changeit`). Debe coincidir con la usada al crearlo. |
| `OMDB_API_KEY` | Clave para el servicio externo OMDb (catálogo de películas/series). |
| `RAWG_API_KEY` | Clave para el servicio externo RAWG (catálogo de videojuegos). |

> Tras modificar `config.properties` hay que **recompilar** (Opción B) para que los cambios
> queden dentro del JAR.

---

## 6. Conexión con el servidor (HTTPS / TLS)

El cliente se conecta al servidor por **HTTPS**. Para validar el certificado, carga al
arrancar un **truststore PKCS12** (`truststore.p12`) desde el *classpath*. Requisitos:

### 6.1. El servidor debe estar accesible

El servidor `StarCritic_Server` debe estar **en ejecución** y escuchando en la dirección y
puerto indicados en `API_BASE_URL` (por defecto `https://172.27.250.6:8443`). Compruébelo,
por ejemplo:

```bash
curl -k https://172.27.250.6:8443/api    # debe responder (no "Connection refused")
```

### 6.2. Resolución del nombre del servidor (si se usa hostname)

Si en `API_BASE_URL` se utiliza un **nombre** en lugar de una IP (p. ej.
`https://starcritic-server:8443/api`), ese nombre debe resolverse a la IP del servidor.
Añádalo al archivo de *hosts* del equipo cliente:

- **Linux/macOS:** `/etc/hosts`
- **Windows:** `C:\Windows\System32\drivers\etc\hosts`

```
172.27.250.6   starcritic-server
```

Actualice esa línea cuando el servidor cambie de máquina/IP.

### 6.3. El truststore debe corresponder al certificado del servidor

El archivo `truststore.p12` incluido debe contener el **certificado del servidor** que se va
a usar. Si el servidor utiliza otro certificado (otra máquina, certificado regenerado…),
debe reconstruir el truststore con el certificado público correcto **antes de compilar**:

```bash
keytool -importcert \
  -alias starcritic-server \
  -file certificado_servidor.crt \
  -keystore src/main/resources/truststore.p12 \
  -storetype PKCS12 \
  -storepass changeit
```

(El nombre del fichero y la contraseña deben coincidir con `TRUSTSTORE_PATH` y
`TRUSTSTORE_PASSWORD` de `config.properties`.)

---

## 7. Compilación (solo Opción B)

### 7.1. Desde línea de comandos (Maven)

Desde la carpeta `StarCritic`:

```bash
mvn clean package
```

- Maven descarga las dependencias (FlatLaf, Gson, JasperReports, PDFBox, JFreeChart…), por lo
  que la **primera** compilación necesita conexión a Internet.
- Se genera el *fat jar* en `target/StarCritic-1.0-SNAPSHOT-all.jar`.

> Si `mvn` no está en el PATH, use el Maven que incluye NetBeans.

### 7.2. Desde NetBeans (recomendado)

1. **File → Open Project** y seleccione la carpeta `StarCritic`.
2. Botón derecho sobre el proyecto → **Clean and Build**.
3. El JAR queda en `target/`.

---

## 8. Ejecución del cliente

### 8.1. Ejecutar el JAR (Opción A o tras compilar)

```bash
java -jar StarCritic-1.0-SNAPSHOT-all.jar
```

> La aplicación crea, en el **directorio desde el que se lanza**, las carpetas de trabajo
> `Certificaciones/` y `Listas/` (exportación de certificaciones y listas). Conviene ejecutar
> el JAR desde una carpeta con permisos de escritura.

### 8.2. Desde NetBeans

Botón derecho sobre el proyecto → **Run**. La clase principal es:

```
com.starcritic.dam_proyect.Main
```

Al arrancar, la aplicación aplica el *Look & Feel* FlatLaf y muestra la **pantalla de inicio
de sesión**.

---

## 9. Comprobación de la instalación

La instalación es correcta cuando:

1. ✅ La aplicación abre la ventana de **inicio de sesión** sin errores en consola.
2. ✅ El inicio de sesión funciona (el cliente alcanza el servidor por HTTPS sin errores de
   certificado).
3. ✅ Los listados de contenidos, búsquedas y críticas se cargan correctamente.
4. ✅ La exportación de informes PDF (JasperReports) funciona y se generan las carpetas
   `Certificaciones/` y `Listas/`.

---

## 10. Resolución de problemas

| Síntoma | Causa probable | Solución |
|---------|----------------|----------|
| `UnsupportedClassVersionError` al arrancar | Java instalado es anterior a 21 | Instale **Java 21** y ajuste `JAVA_HOME`. |
| `Error de comunicación con la API` / `Connection refused` | El servidor no está arrancado o la IP/puerto es incorrecta | Inicie `StarCritic_Server`; revise `API_BASE_URL`. |
| `UnknownHostException` con el nombre del servidor | El hostname no se resuelve | Añada la entrada en el archivo `hosts` (sección 6.2). |
| `PKIX path building failed` / error de certificado TLS | El truststore no contiene el certificado del servidor | Regenere `truststore.p12` con el certificado correcto y recompile (sección 6.3). |
| `No se encontró el truststore 'truststore.p12' en el classpath` | Falta el truststore en `resources` al compilar | Coloque `truststore.p12` en `src/main/resources/` y recompile. |
| `No se encontró config.properties en el classpath` | Falta `config.properties` en `resources` | Cree el archivo (sección 5) y recompile. |
| Cambié la URL/clave pero no surte efecto | `config.properties` va dentro del JAR | Edite el fichero en el código fuente y **recompile** (Opción B). |
| No se generan los PDF / error de escritura | Carpeta de ejecución sin permisos | Lance el JAR desde una carpeta con permisos de escritura. |

---

## 11. Resumen rápido (checklist)

- [ ] Instalar **Java 21** y comprobar `java -version`.
- [ ] Asegurar que el **servidor StarCritic** está en marcha y es accesible por HTTPS.
- [ ] Configurar `config.properties`: `API_BASE_URL` y, si procede, las claves de API.
- [ ] Si se usa un hostname, añadir la entrada en el archivo `hosts` del cliente.
- [ ] Verificar que `truststore.p12` corresponde al certificado del servidor.
- [ ] **Opción A:** copiar `StarCritic-1.0-SNAPSHOT-all.jar`. **Opción B:** `mvn clean package`.
- [ ] Ejecutar: `java -jar StarCritic-1.0-SNAPSHOT-all.jar` (o *Run* en NetBeans).
- [ ] Comprobar login, listados e informes PDF.

---

*Fin del manual de instalación del cliente StarCritic.*
