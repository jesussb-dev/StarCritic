# Refactorización OOP — Star Critic
> Fecha: 2026-05-17

## Motivación

El código original acumulaba varias formas de duplicación:

- Cada controller repetía `private Model model; private XxxView view;` sin herencia común.
- `CriticaAudiovisual` y `CriticaVideojuego` duplicaban el campo `idAspecto` con sus getters/setters.
- Los métodos utilitarios `valueOrEmpty` y `cerrar` estaban copiados en varias clases de la capa de datos.
- 62 `ActionListener` anónimos de una sola línea real ensuciaban todos los controllers.
- Varios `MouseListener` implementaban los cinco métodos aunque solo se usaban uno o dos.

---

## Nuevas clases

### `controller/BaseController.java`

```java
public abstract class BaseController<V> {
    protected final Model model;
    protected final V view;

    protected BaseController(V view, Model model) { ... }
}
```

Clase abstracta genérica heredada por todos los controllers que tienen vista y modelo.
El parámetro de tipo `V` permite que cada subclase acceda a su vista con el tipo concreto sin castings.

---

### `model/pojo/bd/CriticaConAspecto.java`

```
Critica
  └── CriticaConAspecto   ← nueva (abstracta)
        ├── CriticaAudiovisual
        └── CriticaVideojuego
```

Extrae el campo `idAspecto` (con getters/setters) que ambas subclases duplicaban.
Los constructores delegan al padre mediante `super(..., idAspecto)`.

---

## Cambios en clases existentes

### `data/database/OperationsDB.java`

Añadidos dos métodos protegidos compartidos:

| Método | Descripción |
|--------|-------------|
| `protected static String valueOrEmpty(String)` | Devuelve `""` si el valor es `null`. Antes estaba copiado como privado en `ContenidoDB`. |
| `protected static void cerrar(ResultSet, PreparedStatement)` | Cierra recursos JDBC en el bloque `finally`. Antes estaba copiado como privado en `AdminContenidoDB` y `EtiquetaEditorialDB`. |

### `data/database/ContenidoDB.java`

- Eliminado `private static String valueOrEmpty(String)` — ahora heredado de `OperationsDB`.

### `data/database/AdminContenidoDB.java`

- Eliminado `private static void cerrar(ResultSet, PreparedStatement)` — ahora heredado de `OperationsDB`.

### `data/database/EtiquetaEditorialDB.java`

- Ídem.

### `model/pojo/bd/CriticaAudiovisual.java`

- Cambia `extends Critica` → `extends CriticaConAspecto`.
- Eliminados el campo `idAspecto` y sus getter/setter (ahora heredados).
- Constructores delegan `idAspecto` al padre.

### `model/pojo/bd/CriticaVideojuego.java`

- Mismo cambio que `CriticaAudiovisual`.

---

## Controllers refactorizados

Todos los controllers con vista y modelo extienden ahora `BaseController<V>`.
Los campos `view` y `model` dejan de declararse en cada clase y se accede directamente.

| Controller | Vista (`V`) |
|------------|-------------|
| `AddContentController` | `ModifyContentDialog` |
| `AddItemToListController` | `ListsUserDialog` |
| `AdminContentController` | `AdminContentDialog` |
| `AdminUserController` | `ListsUserDialog` |
| `CompleteItemController` | `CompleteItemDialog` |
| `CriticController` | `CriticDialog` |
| `CriticsController` | `CriticsDialog` |
| `ListsUserController` | `ListsUserDialog` |
| `LogInUserController` | `LogInUserDialog` |
| `MainNavigationController` | `MainNavigationFrame` |
| `MessageController` | `MessageDialog` |
| `ModifyContentController` | `ModifyContentDialog` |
| `ProfileController` | `ProfileDialog` |
| `RegisterUserController` | `RegisterDialog` |
| `RevisionCertificationsController` | `SearchDialog` |
| `SearchController` | `SearchDialog` |
| `SearchListController` | `SearchDialog` |
| `UserCriticsController` | `CriticsDialog` |

Controllers excluidos (sin encaje limpio en la base):

- `PDFViewerController` — no tiene `Model`, solo `PDFViewerDialog` + ruta.
- `FileChooserController` — la vista es un `JFileChooser` estándar, no un diálogo propio.

---

## Lambdas y MouseAdapter

**Antes** (62 ocurrencias):

```java
private ActionListener getCancelButtonActionListener() {
    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    };
    return al;
}
```

**Después**:

```java
view.setCancelButtonListener(e -> view.dispose());
```

Los `MouseListener` con 5 métodos vacíos se sustituyeron por `MouseAdapter` sobrescribiendo únicamente los métodos relevantes (`mouseClicked`, `mouseReleased`).

La lógica que antes se delegaba a métodos `getXxxActionListener()` se extrajo a métodos privados con nombres semánticos (`chooseImage()`, `confirmAdd()`, `deleteList()`, etc.).

---

## Bug corregido

**`MessageController`** tenía dos problemas:

1. El botón "Enviar" estaba registrado con el listener de cancelar:
   ```java
   // antes (incorrecto)
   this.view.setSendButtonListener(this.getCancelButtonActionListener());
   ```
   Corregido para llamar al listener de envío real.

2. La validación `isCheckValue()` usaba `||` en lugar de `&&`, invirtiendo la lógica de verificación de campos vacíos.

---

## Estructura de herencia resultante

```
BaseController<V>
├── AddContentController          <ModifyContentDialog>
├── AddItemToListController       <ListsUserDialog>
├── AdminContentController        <AdminContentDialog>
├── AdminUserController           <ListsUserDialog>
├── CompleteItemController        <CompleteItemDialog>
├── CriticController              <CriticDialog>
├── CriticsController             <CriticsDialog>
├── ListsUserController           <ListsUserDialog>
├── LogInUserController           <LogInUserDialog>
├── MainNavigationController      <MainNavigationFrame>
├── MessageController             <MessageDialog>
├── ModifyContentController       <ModifyContentDialog>
├── ProfileController             <ProfileDialog>
├── RegisterUserController        <RegisterDialog>
├── RevisionCertificationsController <SearchDialog>
├── SearchController              <SearchDialog>
├── SearchListController          <SearchDialog>
└── UserCriticsController         <CriticsDialog>

Critica
└── CriticaConAspecto  (abstracta)
      ├── CriticaAudiovisual
      └── CriticaVideojuego
```

---

## Métricas de cambio

### Archivos nuevos creados

| Archivo | Líneas |
|---------|--------|
| `controller/BaseController.java` | 20 |
| `model/pojo/bd/CriticaConAspecto.java` | 30 |
| `resources/refactorizacion.md` | 186 |
| **Total nuevas** | **236** |

### Archivos modificados (git diff)

| Métrica | Valor |
|---------|-------|
| Archivos modificados | 40 |
| Líneas añadidas | +1 163 |
| Líneas eliminadas | −2 355 |
| **Balance neto** | **−1 192 líneas** |

### Resumen global

| Concepto | Líneas |
|----------|--------|
| Código nuevo (clases creadas) | +50 |
| Código modificado añadido | +1 163 |
| Código eliminado | −2 355 |
| **Reducción neta total** | **−1 142 líneas** |

La reducción representa aproximadamente un **33 % menos de código** respecto al total afectado,
eliminando duplicaciones en la capa de datos, clases anónimas en controllers y campos repetidos en POJOs.
