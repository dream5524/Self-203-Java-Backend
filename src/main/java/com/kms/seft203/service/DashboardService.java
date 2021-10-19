package com.kms.seft203.service;
import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.dto.DashboardUpdateDto;
<<<<<<< Updated upstream
=======
import com.kms.seft203.entity.Dashboard;
>>>>>>> Stashed changes
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardNotFoundException;
import javassist.tools.web.BadHttpRequest;

public interface DashboardService {
    DashboardResponseDto save(DashboardCreateDto dashboardDto) throws ContactNotFoundException, BadHttpRequest;
    List<DashboardResponseDto> getAllDashboards();
    DashboardUpdateDto updateById(DashboardUpdateDto dashboardUpdateDto) throws DashboardNotFoundException;
}
