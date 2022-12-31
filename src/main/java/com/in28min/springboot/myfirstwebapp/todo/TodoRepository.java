package com.in28min.springboot.myfirstwebapp.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<ToDo, Integer>{
	
	public List<ToDo> findByUserName(String userName);

}
