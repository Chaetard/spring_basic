package com.principal.todoapp.controller;
// Definición del paquete donde está esta clase Controller, ayuda a organizar el proyecto

import com.principal.todoapp.dto.TaskDTO;
// Importa la clase TaskDTO, que es un Data Transfer Object, para transferir datos de tarea de forma segura y ligera

import com.principal.todoapp.mapper.TaskMapper;
// Importa la clase encargada de convertir entre Task y TaskDTO, para separar lógica de entidad y transporte

import com.principal.todoapp.model.Task;
// Importa la clase entidad Task, representa la tarea en la base de datos y en la lógica de negocio

import com.principal.todoapp.service.TaskService;
// Importa el servicio que contiene la lógica de negocio para gestionar tareas (CRUD)

import org.springframework.http.ResponseEntity;
// Importa ResponseEntity para manejar respuestas HTTP, controlar códigos de estado y contenido

import org.springframework.web.bind.annotation.*;
// Importa las anotaciones REST para definir controladores, rutas y métodos HTTP (GET, POST, etc.)

import java.util.List;
// Importa List, que es una colección ordenada de elementos, usada para manejar múltiples tareas

import java.util.Optional;
// Importa Optional, un contenedor que puede contener o no un valor, para manejar posibles nulls de forma segura

import java.util.stream.Collectors;
// Importa Collectors para transformar streams en listas u otras colecciones

@RestController
// Marca esta clase como controlador REST, donde los métodos responden a peticiones HTTP y devuelven datos JSON automáticamente

@RequestMapping("/tasks")
// Define la ruta base para todos los métodos en este controlador; todos los endpoints empiezan con /tasks

public class TaskController {

    private final TaskService taskService;
    // Declaración de la dependencia TaskService para acceder a la lógica de negocio (CRUD tareas)

    private final TaskMapper taskMapper;
    // Declaración de la dependencia TaskMapper para convertir entre entidades Task y DTOs TaskDTO

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        // Constructor donde se inyectan las dependencias, garantizando que el controlador tenga acceso a servicio y mapper
        this.taskService = taskService;
        // Asigna el servicio recibido al atributo local para usarlo en métodos
        this.taskMapper = taskMapper;
        // Asigna el mapper recibido al atributo local para usarlo en métodos
    }

    // 🔹 Obtener todas las tareas
    @GetMapping
    // Define que este método responde a solicitudes HTTP GET a /tasks
    public List<TaskDTO> getAllTasks() {
        // Retorna una lista de objetos TaskDTO, es decir, la representación ligera de tareas para el cliente
        return taskService.obtenerTodos()
                // Llama al servicio para obtener todas las entidades Task de la base de datos
                .stream()
                // Convierte la lista a un Stream para poder aplicar operaciones funcionales
                .map(taskMapper::toDTO)
                // Por cada Task en el stream, la convierte a TaskDTO usando el método toDTO del mapper
                .collect(Collectors.toList());
        // Recolecta los elementos transformados y los junta de nuevo en una lista para devolver
    }

    // 🔹 Obtener tarea por ID
    @GetMapping("/{id}")
    // Responde a GET /tasks/{id}, donde {id} es un parámetro variable en la URL
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        // Retorna un ResponseEntity que puede contener un TaskDTO o un estado HTTP (ej. 404 si no existe)
        Optional<Task> optionalTask = taskService.obtenerPorId(id);
        // Busca la tarea en el servicio usando el ID recibido, Optional ayuda a manejar si no se encuentra la tarea
        return optionalTask
                // Si la tarea existe:
                .map(task -> ResponseEntity.ok(taskMapper.toDTO(task)))
                // Convierte la tarea a DTO y devuelve un ResponseEntity con código 200 OK y el DTO en el cuerpo
                // Si la tarea NO existe:
                .orElse(ResponseEntity.notFound().build());
        // Devuelve un ResponseEntity con código 404 Not Found y sin cuerpo
    }

    // 🔹 Crear nueva tarea
    @PostMapping
    // Método que responde a POST /tasks para crear una nueva tarea
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        // Recibe en el cuerpo de la petición un TaskDTO con los datos para crear la tarea
        Task nuevaTask = taskMapper.toEntity(taskDTO);
        // Convierte el DTO recibido a la entidad Task para persistirla
        Task guardada = taskService.guardarTask(nuevaTask);
        // Llama al servicio para guardar la nueva tarea en la base de datos y retorna la tarea guardada con ID generado
        return ResponseEntity.ok(taskMapper.toDTO(guardada));
        // Convierte la tarea guardada a DTO y responde con código 200 OK y el DTO en el cuerpo
    }

    // 🔹 Actualizar una tarea existente
    @PutMapping("/{id}")
    // Método que responde a PUT /tasks/{id} para actualizar una tarea existente
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        // Recibe el ID en la URL y los datos actualizados en el cuerpo (como DTO)
        Optional<Task> optionalTask = taskService.obtenerPorId(id);
        // Busca la tarea existente para actualizarla, usando Optional para manejar ausencia de la entidad
        if (optionalTask.isEmpty()) {
            // Si no se encontró la tarea:
            return ResponseEntity.notFound().build();
            // Responde con 404 Not Found sin cuerpo
        }

        Task existente = optionalTask.get();
        // Si existe, extrae la tarea de Optional
        taskMapper.updateEntityFromDTO(taskDTO, existente);
        // Actualiza los campos de la entidad existente con los datos del DTO recibido (sin cambiar el ID)
        Task actualizada = taskService.guardarTask(existente);
        // Guarda la tarea actualizada en la base de datos
        return ResponseEntity.ok(taskMapper.toDTO(actualizada));
        // Devuelve la tarea actualizada como DTO con código 200 OK
    }

    // 🔹 Eliminar tarea por ID
    @DeleteMapping("/{id}")
    // Método que responde a DELETE /tasks/{id} para eliminar una tarea específica
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        // Recibe el ID de la tarea a eliminar desde la URL
        Optional<Task> optionalTask = taskService.obtenerPorId(id);
        // Busca la tarea para confirmar que existe antes de eliminarla
        if (optionalTask.isPresent()) {
            // Si existe la tarea:
            taskService.eliminarTask(id);
            // Llama al servicio para eliminar la tarea usando el ID
            return ResponseEntity.noContent().build();
            // Responde con código 204 No Content que indica éxito sin cuerpo de respuesta
        } else {
            // Si no existe la tarea:
            return ResponseEntity.notFound().build();
            // Responde con código 404 Not Found
        }
    }
}
