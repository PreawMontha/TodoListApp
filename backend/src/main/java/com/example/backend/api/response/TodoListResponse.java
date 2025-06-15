package com.example.backend.api.response;
import java.sql.Timestamp;
import java.util.List;

import com.example.backend.Data.TodoList;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TodoListResponse {
    private String title;
    private String description;
    private Timestamp createDate;
    private Timestamp updatedAt;
    private Integer status;
    private int priority;
    private Boolean isDeleted;

    private List<TodoList> data;
}
