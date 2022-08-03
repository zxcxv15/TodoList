package com.springboot.TodoList.web.dto.todo;

import com.springboot.TodoList.domin.todo.Todo;

import lombok.Data;

@Data
public class UpdateTodoReqDto {
	private int todoCode;
	private String todo;
	
	public Todo toEntity() {
		return Todo.builder()
				.todo_code(todoCode)
				.todo_content(todo)
				.build();
	}
}
