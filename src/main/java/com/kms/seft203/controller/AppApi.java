package com.kms.seft203.controller;

import com.kms.seft203.repository.AppVersionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppApi implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(AppApi.class);

    @Autowired
    private BuildProperties buildProperties;

    @GetMapping("/version")
    @Cacheable("app")
    public String getCurrentVersion() {
        logger.info("Get application info has begun...");
        return "Group: " + buildProperties.getGroup() + "\n"
                + "Name: " + buildProperties.getName() + "\n"
                + "Version:  " + buildProperties.getVersion();
    }
}
