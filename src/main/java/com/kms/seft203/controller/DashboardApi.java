package com.kms.seft203.controller;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.service.DashboardService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboards")
public class DashboardApi {
    private static final Logger logger = LoggerFactory.getLogger(DashboardApi.class);
    @Autowired
    private DashboardService dashboardService;

    /**
     * This method is implemented to handle the save request from FE.
     * Sample of request.body:
     * {
     * "email": "duclocdk1999@gmail.com",
     * "title": "Home page",
     * "layoutType": "Dark mode"
     * }
     * <p>
     * If the request is processed successfully, a response with the same body as request
     * is returned (header - status code is created).
     *
     * @param dashboardDto
     * @return
     * @throws ContactNotFoundException
     * @throws BadHttpRequest
     */
    @PostMapping
    public ResponseEntity<DashboardResponseDto> save(@RequestBody DashboardCreateDto dashboardDto) throws ContactNotFoundException, BadHttpRequest {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dashboardService.save(dashboardDto));
    }

    @GetMapping
    public ResponseEntity<List<DashboardResponseDto>> getAllDashboards() {
        logger.info("Get all dashboards method started...");
        return ResponseEntity.status(HttpStatus.OK)
                .body(dashboardService.getAllDashboards());
    }
}