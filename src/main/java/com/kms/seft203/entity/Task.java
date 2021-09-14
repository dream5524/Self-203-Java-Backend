package com.kms.seft203.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    private String id;
    private String task;
    private Boolean isCompleted;
    private String userId;
}
