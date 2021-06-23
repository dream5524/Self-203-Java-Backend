package com.kms.seft203.task;

import lombok.Data;

@Data
public class Task {
    private String id;
    private String task;
    private Boolean isCompleted;
    private String userId;
}
