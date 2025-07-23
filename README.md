# Arquitectura y Flujo de la Aplicación TodoApp

## 1. Capas principales

- **Model (Entidad)**
    - Clase `Task` representa la tabla en base de datos con campos id, title, description y completed.
    - Anotada con JPA para mapear automáticamente a tabla relacional.

- **Repository (Persistencia)**
    - Interfaz `TaskRepository` que extiende JpaRepository para acceso a datos CRUD.
    - No requiere implementación manual, Spring Data genera el código.

- **Service (Lógica de Negocio)**
    - Clase `TaskService` que contiene métodos para obtener, guardar, actualizar y eliminar tareas.
    - Usa `TaskRepository` para manipular datos en BD.
    - Sirve para centralizar la lógica y facilitar pruebas unitarias.

- **DTO (Data Transfer Object)**
    - Clase `TaskDTO` que es una versión ligera de `Task`, solo con datos necesarios.
    - Evita exponer directamente la entidad a la capa externa (cliente).

- **Mapper (Convertidor)**
    - Clase `TaskMapper` convierte entre `Task` y `TaskDTO`.
    - Permite separar la lógica de transformación para mantener el código limpio.

- **Controller (API REST)**
    - Clase `TaskController` expone los endpoints REST para manejar tareas.
    - Recibe peticiones HTTP, usa el servicio y el mapper para procesar datos y devolver respuestas.
    - Controla códigos de estado HTTP y respuestas para cliente.

---

## 2. Flujo típico para una operación (Ejemplo: Obtener todas las tareas)

Cliente → HTTP GET `/tasks` →  
**TaskController** llama a → **TaskService.obtenerTodos()** →  
**TaskService** llama a → **TaskRepository.findAll()** →  
BD devuelve lista de entidades `Task` →  
**TaskController** usa → **TaskMapper.toDTO()** para convertir cada `Task` a `TaskDTO` →  
Se devuelve al cliente una lista JSON con las tareas.

---

## 3. Por qué usar DTO y Mapper

- DTOs limitan la información que se expone, protegiendo la entidad.
- Mapper centraliza la conversión, evita duplicar código en el controlador.
- Facilita mantenimiento y evolución de la API.

---

## 4. Manejo de Optional

- Para búsquedas por ID, se usa `Optional<Task>` para manejar la posible ausencia de datos sin lanzar errores.
- Permite respuestas HTTP 404 Not Found cuando la tarea no existe.

---

## 5. Respuesta HTTP con ResponseEntity

- Permite controlar código HTTP (200 OK, 404 Not Found, 204 No Content).
- Agrega flexibilidad para incluir o no cuerpo en la respuesta.

---

## 6. Inyección de dependencias

- Las dependencias `TaskService` y `TaskMapper` se inyectan en el constructor del controlador.
- Esto mejora la modularidad y facilita las pruebas unitarias.

---

## 7. Endpoints principales

| Método | Ruta        | Acción                       | Entrada        | Salida              |
|--------|-------------|-----------------------------|----------------|---------------------|
| GET    | /tasks      | Obtener lista de tareas      | Ninguna        | List<TaskDTO>       |
| GET    | /tasks/{id} | Obtener tarea por ID         | ID en URL      | TaskDTO o 404        |
| POST   | /tasks      | Crear tarea nueva            | TaskDTO en body| TaskDTO con ID      |
| PUT    | /tasks/{id} | Actualizar tarea existente   | ID en URL + DTO| TaskDTO actualizado |
| DELETE | /tasks/{id} | Eliminar tarea por ID        | ID en URL      | 204 No Content o 404 |

---

Este esquema y explicación te ayudarán a entender cómo cada clase y capa tiene un propósito específico y cómo se conectan para brindar una API REST limpia y funcional para administrar tareas. ¿Quieres que te prepare también un diagrama visual o algo más?