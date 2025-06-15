package com.example.backend.api.request;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class InsertItemRequest {
    private String title;
    private String description;
    private int priority;
    private Integer status;
    private Boolean isDeleted;
}
