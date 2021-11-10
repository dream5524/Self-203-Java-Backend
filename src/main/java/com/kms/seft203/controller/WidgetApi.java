package com.kms.seft203.controller;

import com.kms.seft203.dto.WidgetDto;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.service.WidgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/widget")
public class WidgetApi {
    private static final Logger logger = LoggerFactory.getLogger(WidgetApi.class);
    @Autowired
    private WidgetService widgetService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody WidgetDto widgetDto) throws DashboardNotFoundException {
        logger.info("Saving widget has begun...{}", widgetDto );
        widgetService.save(widgetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Widget was created successfully !");
    }
}
