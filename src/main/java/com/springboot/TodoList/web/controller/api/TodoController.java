package com.springboot.TodoList.web.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.TodoList.service.todo.TodoService;
import com.springboot.TodoList.web.dto.CMRespDto;
import com.springboot.TodoList.web.dto.todo.CreateTodoReqDto;
import com.springboot.TodoList.web.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todolist")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@PostMapping("/content")
	public ResponseEntity<?> addTodo(@RequestBody CreateTodoReqDto createTodoReqDto){
		try {
			if(!todoService.createTodo(createTodoReqDto)) {
				throw new RuntimeException("DataBase Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"Adding todo failed",createTodoReqDto));
		}	
		return ResponseEntity.ok().body(new CMRespDto<>(1,"success",createTodoReqDto));
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getTodolist(@RequestParam int page, @RequestParam int contentCount){
		List<TodoListRespDto> list = null;
		try {
			list = todoService.getTodoLsit(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"page list on load failed",list));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1,"page list success to load",list));
	}
	
	@GetMapping("/list/importance")
	public ResponseEntity<?> getImportanceTodoList(@RequestParam int page, @RequestParam int contentCount){
		List<TodoListRespDto> list = null;
		try {
			list = todoService.getImportanceTodoList(page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1,"page importance list on load failed",list));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1,"page importance list success to load",list));
	}
	
	
}
