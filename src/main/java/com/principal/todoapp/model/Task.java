package com.principal.todoapp.model;

import jakarta.persistence.*;

// Clase entidad que representa la tabla 'Task' en la base de datos
@Entity
public class Task {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   // Marca 'id' como clave primaria y su generación automática (auto-incremental)
   private Long id;

   private String title;       // Columna para título de la tarea
   private String description; // Columna para descripción de la tarea
   private boolean completed;  // Columna para estado completado (true/false)

   // Constructor vacío obligatorio para JPA (Java Persistence API)
   public Task() {}

   // Constructor opcional para crear tareas fácilmente en código
   public Task(String title, String description, boolean completed) {
      this.title = title;
      this.description = description;
      this.completed = completed;
   }

   // Métodos GET para acceder a los atributos privados (encapsulamiento)
   public Long getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }

   public boolean isCompleted() {
      return completed;
   }

   // Métodos SET para modificar atributos privados (encapsulamiento)
   public void setId(Long id) {
      this.id = id;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }
}