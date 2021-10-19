package com.kms.seft203.service;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardRequestDto;
import com.kms.seft203.dto.DashboardUpdateDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardDuplicatedException;
import com.kms.seft203.exception.DashboardNotFoundException;

import java.util.List;

public interface DashboardService {
    DashboardRequestDto save(DashboardCreateDto dashboardDto) throws ContactNotFoundException, DashboardDuplicatedException;
    List<DashboardRequestDto> getAllDashboards();
    DashboardUpdateDto updateById(DashboardUpdateDto dashboardUpdateDto) throws DashboardNotFoundException;
}
