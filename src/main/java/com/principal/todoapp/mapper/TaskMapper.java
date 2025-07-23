package com.principal.todoapp.mapper;

import com.principal.todoapp.dto.TaskDTO;
import com.principal.todoapp.model.Task;

// Clase encargada de convertir entre la entidad Task y su DTO TaskDTO
public class TaskMapper {

    // Convierte una entidad Task a su representación DTO para enviar al cliente
    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),            // Mapea el ID de la tarea
                task.getTitle(),         // Mapea el título de la tarea
                task.getDescription(),   // Mapea la descripción de la tarea
                task.isCompleted()       // Mapea el estado completado
        );
    }

    // Convierte un DTO recibido del cliente a una entidad Task para guardar en base de datos
    public Task toEntity(TaskDTO dto) {
        Task task = new Task();      // Crea una nueva instancia de Task (entidad)
        task.setId(dto.getId());     // Asigna ID (puede ser null si es nueva tarea)
        task.setTitle(dto.getTitle());           // Asigna título
        task.setDescription(dto.getDescription());// Asigna descripción
        task.setCompleted(dto.isCompleted());    // Asigna estado completado
        return task;                 // Retorna la entidad lista para persistir
    }

    // Actualiza una entidad Task existente con los valores de un DTO (sin cambiar el ID)
    public void updateEntityFromDTO(TaskDTO dto, Task task) {
        task.setTitle(dto.getTitle());           // Actualiza título
        task.setDescription(dto.getDescription());// Actualiza descripción
        task.setCompleted(dto.isCompleted());    // Actualiza estado completado
    }
}
