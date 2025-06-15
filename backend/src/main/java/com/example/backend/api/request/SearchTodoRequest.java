package com.example.backend.api.request;

import lombok.Data;

@Data
public class SearchTodoRequest {
    private String title;
     private int pageNumber;
    private int rowPerPage;
}
