package com.example.backend.Service;


import org.springframework.context.ApplicationContextException;

import com.example.backend.Data.TodoList;
import com.example.backend.api.response.SearchResult;

public interface TodoListService {

    public void insertTodoList(TodoList poTodoList) throws ApplicationContextException;
    // public List<TodoList> getTodoList();
     public SearchResult<TodoList> getTodoListPaged(int pageNumber, int rowPerPage);
     public void updateTodoList(TodoList poTodoList) throws ApplicationContextException;
     public TodoList getTodoListById(int id) throws ApplicationContextException;
     public void deleteTodoListById(int id) throws ApplicationContextException;
    public SearchResult<TodoList> searchTodoListByTitlePaged(String title, int pageNumber, int rowPerPage) ;
}
