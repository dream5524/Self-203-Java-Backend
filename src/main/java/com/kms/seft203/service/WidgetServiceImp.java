package com.kms.seft203.service;

import com.kms.seft203.dto.WidgetDto;
import com.kms.seft203.entity.Dashboard;
import com.kms.seft203.entity.Widget;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.repository.DashboardRepository;
import com.kms.seft203.repository.WidgetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WidgetServiceImp implements WidgetService {
    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WidgetDto save(WidgetDto widgetDto) throws DashboardNotFoundException {
        Optional<Dashboard> optionalDashboard = dashboardRepository.findById(widgetDto.getDashboard_id());
        if (optionalDashboard.isEmpty()) {
            throw new DashboardNotFoundException("Dashboard with id: " + widgetDto.getDashboard_id() +
                    " does not exist.");
        }
        Dashboard dashboardFromDb = optionalDashboard.get();
        Widget widget = modelMapper.map(widgetDto, Widget.class);
        widget.setDashboard(dashboardFromDb);
        Widget saveWidget = widgetRepository.save(widget);
        return modelMapper.map(saveWidget, WidgetDto.class);
    }
}
