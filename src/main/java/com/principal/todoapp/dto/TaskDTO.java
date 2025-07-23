package com.principal.todoapp.dto;

// Clase para transportar datos de una tarea entre cliente y servidor sin exponer la entidad directamente
public class TaskDTO {

    private Long id; // Identificador único de la tarea (puede ser null en nuevas tareas)
    private String title; // Título de la tarea (texto descriptivo corto)
    private String description; // Descripción más detallada de la tarea
    private boolean completed; // Estado que indica si la tarea está completada o no

    public TaskDTO() {} // Constructor vacío necesario para frameworks que hacen deserialización automática (ej. JSON a objeto)

    // Constructor completo para facilitar creación de objetos TaskDTO con todos sus datos
    public TaskDTO(Long id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // Métodos GET para acceder a los atributos privados (encapsulamiento)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }

    // Métodos SET para modificar los atributos privados (encapsulamiento)
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}