package com.principal.todoapp.service;

import com.principal.todoapp.model.Task;
import com.principal.todoapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
// Marca la clase como servicio Spring, para lógica de negocio y que pueda ser inyectada en controladores
public class TaskService {

    private final TaskRepository taskRepository;
    // Dependencia al repositorio para acceder a la base de datos

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
        // Inyección por constructor para mantener la dependencia segura y testeable
    }

    // Método para obtener todas las tareas desde la base de datos
    public List<Task> obtenerTodos(){
        return taskRepository.findAll();
        // findAll() es método de JpaRepository que retorna una lista con todas las tareas
    }

    // Método para buscar una tarea por su ID
    public Optional<Task> obtenerPorId(long id){
        return taskRepository.findById(id);
        // findById devuelve un Optional<Task>, para manejar si no existe la tarea con ese ID
    }

    // Método para eliminar una tarea por su ID
    public void eliminarTask(Long id){
        taskRepository.deleteById(id);
        // deleteById elimina la tarea con el ID proporcionado
    }

    // Método para guardar o actualizar una tarea (si no tiene ID, se crea; si tiene, se actualiza)
    public Task guardarTask(Task task){
        return taskRepository.save(task);
        // save guarda o actualiza la tarea en la base de datos y devuelve la tarea guardada
    }
}
