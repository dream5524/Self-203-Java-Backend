package com.kms.seft203.service;

import com.kms.seft203.dto.WidgetDto;
import com.kms.seft203.exception.DashboardNotFoundException;

public interface WidgetService {
    WidgetDto save(WidgetDto widgetDto) throws DashboardNotFoundException;
}
