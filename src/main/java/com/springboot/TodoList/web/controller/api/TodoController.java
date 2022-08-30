package com.springboot.TodoList.web.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.TodoList.service.todo.TodoService;
import com.springboot.TodoList.web.dto.CMRespDto;
import com.springboot.TodoList.web.dto.todo.CreateTodoReqDto;
import com.springboot.TodoList.web.dto.todo.TodoListRespDto;
import com.springboot.TodoList.web.dto.todo.UpdateTodoReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todolist")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@GetMapping("/list/{type}")
	public ResponseEntity<?> getTodoList(@PathVariable String type, @RequestParam int page, @RequestParam int contentCount) {
		List<TodoListRespDto> list = null; // 메소드 안에서의 전역 변수로 잡아줘야 딴데서도 쓸 수 있다.
		try {
			list = todoService.getTodoList(type, page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, page + "page list on load failed", list));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, page + "page list success to load", list));
	}
	
	@PostMapping("/todo")
	public ResponseEntity<?> addTodo(@RequestBody CreateTodoReqDto createTodoReqDto) {
		boolean status = false;
		try {
			status = todoService.createTodo(createTodoReqDto);
			if(!status) {
				throw new RuntimeException("DataBase Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Adding todo failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@PutMapping("/complete/todo/{todoCode}")
	public ResponseEntity<?> setCompleteTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			status = todoService.updateTodoComplete(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@PutMapping("/importance/todo/{todoCode}")
	public ResponseEntity<?> setImportanceTodo(@PathVariable int todoCode) {
		boolean status = false;
		try {
			status = todoService.updateTodoImportance(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@PutMapping("/todo/{todoCode}")
	public ResponseEntity<?> setTodo(@PathVariable int todoCode, @RequestBody UpdateTodoReqDto updateTodoReqDto) {
		boolean status = false;
		try {
			updateTodoReqDto.setTodoCode(todoCode);
			status = todoService.updateTodo(updateTodoReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
	
	@DeleteMapping("/todo/{todoCode}")
	public ResponseEntity<?> removeTodo(@PathVariable int todoCode){
		boolean status = false;
		try {
			status = todoService.removeTodo(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "success", status));
	}
}

