package com.in28min.springboot.myfirstwebapp.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResource {

	private TodoRepository todoRepostiory;

	public TodoResource(TodoRepository todoRepostiory) {
		super();
		this.todoRepostiory = todoRepostiory;
	}
	
	@GetMapping(value = "/baseauthtoken")
	public String basicAuth() {
		return "Sucess";
	}

	@GetMapping(value = "/users/{username}/todos")
	public List<ToDo> retreiveTodos(@PathVariable String username) {

		return todoRepostiory.findByUserName(username);

	}

	@GetMapping(value = "/users/{username}/todo/{id}")
	public ToDo retreiveTodoById(@PathVariable String username, @PathVariable int id) {

		return todoRepostiory.findById(id).get();

	}

	@DeleteMapping(value = "/users/{username}/todo/{id}")
	public ResponseEntity<Void> deleteTodoById(@PathVariable String username, @PathVariable int id) {

		todoRepostiory.deleteById(id);
		return ResponseEntity.noContent().build();

	}

	@PostMapping(value = "/users/{username}/todo")
	public ResponseEntity<Void> addTodoById(@PathVariable String username, @RequestBody ToDo toDo) {

		todoRepostiory.save(toDo);
		return ResponseEntity.created(null).build();

	}

	@PutMapping(value = "/users/{username}/todo/{id}")
	public ToDo updateTodoById(@PathVariable String username, @PathVariable int id,
			@RequestBody ToDo toDo) {
		todoRepostiory.deleteById(id);
		todoRepostiory.save(toDo);
		return toDo;
	}

}
