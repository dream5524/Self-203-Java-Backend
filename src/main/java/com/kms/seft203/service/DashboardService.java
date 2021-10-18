package com.kms.seft203.service;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardDuplicatedException;
import javassist.tools.web.BadHttpRequest;

import java.util.List;

public interface DashboardService {
    DashboardResponseDto save(DashboardCreateDto dashboardDto) throws ContactNotFoundException, DashboardDuplicatedException;
    List<DashboardResponseDto> getAllDashboards();
}
