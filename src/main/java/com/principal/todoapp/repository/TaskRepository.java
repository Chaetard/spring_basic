package com.principal.todoapp.repository;

import com.principal.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfaz que extiende JpaRepository para proporcionar operaciones CRUD automáticas
// JpaRepository provee métodos para guardar, buscar, eliminar, actualizar entidades Task
public interface TaskRepository extends JpaRepository<Task, Long> {
    // No hace falta implementar métodos, Spring Data JPA lo hace automáticamente
}