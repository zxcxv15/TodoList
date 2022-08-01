package com.springboot.TodoList.service.todo;


import java.util.List;

import com.springboot.TodoList.web.dto.todo.CreateTodoReqDto;
import com.springboot.TodoList.web.dto.todo.TodoListRespDto;

public interface TodoService {
	//추가
	public boolean createTodo(CreateTodoReqDto createTodoReqDto) throws Exception;
	//조회
	public List<TodoListRespDto> getTodoLsit(int page, int contentCount) throws Exception;
	public List<TodoListRespDto> getImportanceTodoList(int page, int contentCount) throws Exception;
}
