package com.kms.seft203.service;

import com.kms.seft203.dto.DashboardDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardDuplicatedException;
import javassist.tools.web.BadHttpRequest;

public interface DashboardService {
    DashboardDto save(DashboardDto dashboardDto) throws ContactNotFoundException, DashboardDuplicatedException;
}
