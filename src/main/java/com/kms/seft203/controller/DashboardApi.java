package com.kms.seft203.controller;

import com.kms.seft203.dto.DashboardDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.service.DashboardService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboards")
public class DashboardApi {
    @Autowired
    private DashboardService dashboardService;

    @PostMapping
    public ResponseEntity<DashboardDto> save(@RequestBody DashboardDto dashboardDto) throws ContactNotFoundException, BadHttpRequest {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dashboardService.save(dashboardDto));
    }
}