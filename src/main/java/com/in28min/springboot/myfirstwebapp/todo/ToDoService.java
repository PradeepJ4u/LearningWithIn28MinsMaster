package com.in28min.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class ToDoService {

	private static List<ToDo> todoList = new ArrayList<>();

	private static int toDoCount = 0;

	static {
		todoList.add(new ToDo(++toDoCount, "Pradeep", "Learn AWS", LocalDate.now().plusYears(1), false));
		todoList.add(new ToDo(++toDoCount, "Pradeep", "Learn Cloud", LocalDate.now().plusYears(2), true));
		todoList.add(new ToDo(++toDoCount, "Swasti", "Learn English", LocalDate.now().plusYears(3), false));
		todoList.add(new ToDo(++toDoCount, "Vedansh", "Learn Maths", LocalDate.now().plusYears(4), false));
		todoList.add(new ToDo(++toDoCount, "Mona", "Learn less Gussa", LocalDate.now().plusYears(5), false));
		todoList.add(new ToDo(++toDoCount, "Pradeep", "Learn Mainframe", LocalDate.now().plusYears(6), false));
	}

	public List<ToDo> findByUserName(String userName) {
		Predicate<? super ToDo> predicate = todo -> todo.getUserName().equalsIgnoreCase(userName);
		return todoList.stream().filter(predicate).toList();
		//return todoList;
	}

	public ToDo findById(int id) {
		Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
		return todoList.stream().filter(predicate).findFirst().get();
	}

	public void addToDo(String userName, String description, LocalDate compilitionDate) {
		ToDo toDo = new ToDo(++toDoCount, userName, description, compilitionDate, false);
		todoList.add(toDo);
	}

	public void deleteToDo(int id) {
		Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
		todoList.removeIf(predicate);
	}

	public void updateToDo(@Valid ToDo todo) {
		deleteToDo(todo.getId());
		todoList.add(todo);
	}

}
