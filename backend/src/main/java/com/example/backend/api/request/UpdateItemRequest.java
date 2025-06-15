package com.example.backend.api.request;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private int id;
    private String title;
    private String description;
    private Integer status;
    private Integer priority;
    private Boolean isDeleted;
}
