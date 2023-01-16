package com.in28min.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class ToDoControllerJPA {

	private TodoRepository todoRepostiory;

	public ToDoControllerJPA(TodoRepository todoRepostiory) {
		super();
		this.todoRepostiory = todoRepostiory;
	}

	@RequestMapping(value = "list-todos")
	public String listDoDos(ModelMap model) {
		String name = getLoggedUserName(model);
		List<ToDo> listToDo = todoRepostiory.findByUserName(name);
		model.put("listToDo", listToDo);
		return "listTodos";
	}
	
	@RequestMapping(value = "/")
	public String welcomePage(ModelMap model) {
		return "welcome";
	}


	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String addDoDos(ModelMap model) {
		String name = getLoggedUserName(model);
		ToDo todo = new ToDo(0, name, "Default Desc", LocalDate.now(), false);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String saveDoDos(ModelMap model, @Valid @ModelAttribute("todo") ToDo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		 String name = getLoggedUserName(model);
		todo.setUserName(name);
		todoRepostiory.save(todo);
		return "redirect:list-todos";
	}

	@RequestMapping(value = "delete-todo", method = RequestMethod.GET)
	public String deleteDoDos(@RequestParam int id) {
		todoRepostiory.deleteById(id);
		return "redirect:list-todos";
	}

	@RequestMapping(value = "update-todo", method = RequestMethod.GET)
	public String updateDoDosGet(@RequestParam int id, ModelMap model) {
		model.put("todo", todoRepostiory.findById(id).get());
		return "todo";
	}

	@RequestMapping(value = "update-todo", method = RequestMethod.POST)
	public String updateDoDosPost(ModelMap model, @Valid @ModelAttribute("todo") ToDo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		todoRepostiory.save(todo);
		return "redirect:list-todos";
	}

	private String getLoggedUserName(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
