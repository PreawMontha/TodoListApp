package com.example.backend.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Data.TodoList;
import com.example.backend.Service.TodoListService;
import com.example.backend.api.request.InsertItemRequest;
import com.example.backend.api.request.SearchTodoRequest;
import com.example.backend.api.request.UpdateItemRequest;
import com.example.backend.api.response.Response;
import com.example.backend.api.response.SearchResult;
import com.example.backend.api.response.TodoListResponse;

@RestController
@RequestMapping("/api/TodoService")
public class TodoLisrController {

@Autowired
   private TodoListService todoListService;

   String status = "FAILED";
   String message = "DATA NOT FOUND";

   @GetMapping("/getTodoList")
    public Response<SearchResult<TodoList>> getTodoList(
        @RequestParam(defaultValue = "1") int pageNumber,
        @RequestParam(defaultValue = "10") int rowPerPage) {

        SearchResult<TodoList> searchResult = todoListService.getTodoListPaged(pageNumber, rowPerPage);

        Response<SearchResult<TodoList>> response = new Response<>();
        response.setStatus("SUCCESS");
        response.setMessage("โหลดข้อมูลสำเร็จ");
        response.setDateTime(new Date());
        response.setData(searchResult);

        return response;
    }

    @PostMapping("/insertTodoList")
    public Response<TodoListResponse> insertTodoList(@RequestBody @Validated InsertItemRequest todoRequest) {
    Response<TodoListResponse> response = new Response<>();
    

    try {
        // Validate input
        StringBuilder errorMsg = new StringBuilder();
        if (todoRequest.getTitle() == null || todoRequest.getTitle().isEmpty()) {
            errorMsg.append(", หัวข้อ");
        }
        if (errorMsg.length() > 0) {
            throw new Exception("กรุณาระบุ" + errorMsg.substring(1));
        }

        // แปลง Request เป็น Entity
        TodoList todoList = new TodoList();
        todoList.setTitle(todoRequest.getTitle());
        todoList.setDescription(todoRequest.getDescription());
        todoList.setStatus(todoRequest.getStatus());
        todoList.setPriority(todoRequest.getPriority());
        todoList.setIsDeleted(todoRequest.getIsDeleted() != null ? todoRequest.getIsDeleted() : false);

        // เรียก service insert
        todoListService.insertTodoList(todoList);

        // แปลง Entity เป็น Response DTO
        TodoListResponse respData = new TodoListResponse();
        respData.setTitle(todoList.getTitle());
        respData.setDescription(todoList.getDescription());
        respData.setCreateDate(todoList.getCreateDate());
        respData.setUpdatedAt(todoList.getUpdatedAt());
        respData.setStatus(todoList.getStatus());
        respData.setPriority(todoList.getPriority());
        respData.setIsDeleted(todoList.getIsDeleted());

        // ส่งกลับ
        response.setStatus("SUCCESS");
        response.setMessage("บันทึกข้อมูลเรียบร้อยแล้ว");
        response.setDateTime(new Date());
        response.setData(respData);

    } catch (Exception e) {
        response.setStatus(status);
        response.setMessage(e.getMessage());
        response.setDateTime(new Date());
        response.setData(null);
    }
    return response;
    }

    @PostMapping("/updateTodoList")
    public Response<TodoListResponse> updateTodoList(@RequestBody @Validated UpdateItemRequest todoRequest) {
        Response<TodoListResponse> response = new Response<>();
        try {
            // Validate input
            StringBuilder errorMsg = new StringBuilder();
            if (todoRequest.getId() == 0) {
                errorMsg.append(", รหัสข้อมูล");
            }
            if (todoRequest.getTitle() == null || todoRequest.getTitle().isEmpty()) {
                errorMsg.append(", หัวข้อ");
            }
            if (errorMsg.length() > 0) {
                throw new Exception("กรุณาระบุ" + errorMsg.substring(1));
            }

            // แปลง Request เป็น Entity
            TodoList todoList = new TodoList();
            todoList.setId(todoRequest.getId());  // สำคัญ ต้องกำหนด id สำหรับ update
            todoList.setTitle(todoRequest.getTitle());
            todoList.setDescription(todoRequest.getDescription());
            todoList.setStatus(todoRequest.getStatus());
            todoList.setPriority(todoRequest.getPriority());
            todoList.setIsDeleted(todoRequest.getIsDeleted() != null ? todoRequest.getIsDeleted() : false);

            // เรียก service update
            todoListService.updateTodoList(todoList);

            // แปลง Entity เป็น Response DTO
            TodoListResponse respData = new TodoListResponse();
            respData.setTitle(todoList.getTitle());
            respData.setDescription(todoList.getDescription());
            respData.setCreateDate(todoList.getCreateDate());
            respData.setUpdatedAt(todoList.getUpdatedAt());
            respData.setStatus(todoList.getStatus());
            respData.setPriority(todoList.getPriority());
            respData.setIsDeleted(todoList.getIsDeleted());

            // ส่งกลับ
            response.setStatus("SUCCESS");
            response.setMessage("แก้ไขข้อมูลเรียบร้อยแล้ว");
            response.setDateTime(new Date());
            response.setData(respData);

        } catch (Exception e) {
            response.setStatus(status);
            response.setMessage(e.getMessage());
            response.setDateTime(new Date());
            response.setData(null);
        }

        return response;
    }

    @GetMapping("/getTodoListById/{id}")
    public Response<TodoListResponse> getTodoListById(@PathVariable("id") int id) {
        Response<TodoListResponse> response = new Response<>();
        try {
            TodoList todo = todoListService.getTodoListById(id);

            TodoListResponse respData = new TodoListResponse();
            respData.setTitle(todo.getTitle());
            respData.setDescription(todo.getDescription());
            respData.setCreateDate(todo.getCreateDate());
            respData.setUpdatedAt(todo.getUpdatedAt());
            respData.setStatus(todo.getStatus());
            respData.setPriority(todo.getPriority());
            respData.setIsDeleted(todo.getIsDeleted());

            response.setStatus("SUCCESS");
            response.setMessage("โหลดข้อมูลสำเร็จ");
            response.setDateTime(new Date());
            response.setData(respData);
        } catch (Exception e) {
            response.setStatus(status);
            response.setMessage(e.getMessage());
            response.setDateTime(new Date());
            response.setData(null);
        }

        return response;
    }
   
    @PostMapping("/searchTodoList")
    public Response<SearchResult<TodoListResponse>> searchTodoList(
        @RequestBody SearchTodoRequest request) {

        String title = request.getTitle();
        int pageNumber = request.getPageNumber();
        int rowPerPage = request.getRowPerPage();

        SearchResult<TodoList> searchResult = todoListService.searchTodoListByTitlePaged(title, pageNumber, rowPerPage);

        List<TodoListResponse> respData = searchResult.getResultList().stream().map(todo -> {
            TodoListResponse resp = new TodoListResponse();
            resp.setTitle(todo.getTitle());
            resp.setDescription(todo.getDescription());
            resp.setCreateDate(todo.getCreateDate());
            resp.setUpdatedAt(todo.getUpdatedAt());
            resp.setStatus(todo.getStatus());
            resp.setPriority(todo.getPriority());
            resp.setIsDeleted(todo.getIsDeleted());
            return resp;
        }).collect(Collectors.toList());

        SearchResult<TodoListResponse> respSearchResult = new SearchResult<>();
        respSearchResult.setResultList(respData);
        respSearchResult.setTotalRecords(searchResult.getTotalRecords());
        respSearchResult.setRowPerPage(searchResult.getRowPerPage());
        respSearchResult.setCurrentPage(searchResult.getCurrentPage());
        respSearchResult.setTotalPages(searchResult.getTotalPages());

        Response<SearchResult<TodoListResponse>> response = new Response<>();
        response.setStatus("SUCCESS");
        response.setMessage("ค้นหาข้อมูลสำเร็จ");
        response.setDateTime(new Date());
        response.setData(respSearchResult);

        return response;
    }

    @PostMapping("/deleteTodoList/{id}")
    public Response<String> deleteTodoList(@PathVariable("id") int id) {
        Response<String> response = new Response<>();
        try {
            if (id == 0) {
                throw new Exception("กรุณาระบุรหัสข้อมูลที่ต้องการลบ");
            }

            // เรียก service ลบข้อมูล
            todoListService.deleteTodoListById(id);

            // ส่งกลับ
            response.setStatus("SUCCESS");
            response.setMessage("ลบข้อมูลเรียบร้อยแล้ว");
            response.setDateTime(new Date());

        } catch (Exception e) {
            response.setStatus(status);
            response.setMessage(e.getMessage());
            response.setDateTime(new Date());
            response.setData(null);
        }

        return response;
    }

}
