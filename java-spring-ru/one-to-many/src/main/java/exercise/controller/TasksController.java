package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<TaskDTO> allTasks() {
        return taskRepository.findAll().stream().map(t -> taskMapper.map(t)).toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO someTask(@PathVariable Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        return taskMapper.map(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO makeTask(@RequestBody TaskCreateDTO incomingDto) {
        var entity = taskMapper.map(incomingDto);
        taskRepository.save(entity);
        return taskMapper.map(entity);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purgeTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public TaskDTO adjustTask(@PathVariable Long id, @RequestBody TaskUpdateDTO incomingDto) {
        var entity = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("") );
        taskMapper.update(incomingDto,entity);
        entity.setAssignee(userRepository.findById(incomingDto.getAssigneeId()).orElseThrow());
        taskRepository.save(entity);
        return taskMapper.map(entity);
     }


    // END
}
