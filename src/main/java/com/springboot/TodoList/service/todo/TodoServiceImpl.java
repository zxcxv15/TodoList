package com.springboot.TodoList.service.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.springboot.TodoList.domin.todo.Todo;
import com.springboot.TodoList.domin.todo.TodoRepository;
import com.springboot.TodoList.web.dto.todo.CreateTodoReqDto;
import com.springboot.TodoList.web.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

	private final TodoRepository todoRepository;
	
	@Override
	public boolean createTodo(CreateTodoReqDto createTodoReqDto) throws Exception {
		Todo todoEntity = createTodoReqDto.toEntity();
//		String content = todoEntity.getTodo_content();
//		for (int i = 100; i < 200; i++) {
//			todoEntity.setTodo_content(content + "_" + (i + 1));
//			if (i % 2 == 0) {
//				todoEntity.setImportance_flag(1);
//
//			} else {
//				todoEntity.setImportance_flag(0);
//			}
//			todoRepository.save(todoEntity);
//		}
//		return true;
			return todoRepository.save(todoEntity) > 0;
	}

	@Override
	public List<TodoListRespDto> getTodoLsit(int page, int contentCount) throws Exception {
		
		List<Todo> todoList = todoRepository.getTodoList(createGetTodoListMap(page, contentCount));
		
		return createTodoListRespDtos(todoList);
	}

	@Override
	public List<TodoListRespDto> getImportanceTodoList(int page, int contentCount) throws Exception {
		
		List<Todo> todoList = todoRepository.getImportanceTodoListOfIndex(createGetTodoListMap(page, contentCount));
	
		return createTodoListRespDtos(todoList);
	
	}
	
	//중복 코드 캡슐화
	private Map<String, Object> createGetTodoListMap(int page, int contentCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index",(page - 1) * contentCount);
		map.put("count",contentCount);
		return map;
	}
	
	private List<TodoListRespDto> createTodoListRespDtos(List<Todo> todoList){
		List<TodoListRespDto> todoListRespDtos = new ArrayList<TodoListRespDto>();
		
		todoList.forEach(todo ->{
			todoListRespDtos.add(todo.toListDto());
		});
		return todoListRespDtos;
		
	}
}
