package com.kms.seft203.service;

import com.kms.seft203.dto.WidgetDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Dashboard;
import com.kms.seft203.entity.User;
import com.kms.seft203.entity.Widget;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.repository.DashboardRepository;
import com.kms.seft203.repository.WidgetRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class WidgetServiceTest {
    @Autowired
    private WidgetService widgetService;

    @MockBean
    private WidgetRepository widgetRepository;

    @MockBean
    private DashboardRepository dashboardRepository;

//    @Test
//    void saveTest_WhenDashboardIsFound_ThenReturnWidgetDto() throws DashboardNotFoundException {
//        Config typeWidgets = new Config(70, 70, 70, 70);
//        Map<String, Config> configs = new HashMap<>();
//        configs.put("type_widget", typeWidgets);
//        WidgetDto expectedWidgetDto = new WidgetDto(1, "chart", 70, 70, configs);
//
//        User user = new User(1, "huyenmo@gmail.com", "1qaz@@123QA", "Huyen Mo");
//        Contact contact = new Contact("Huyen", "Mo", user, "Tester", "Build Dashboard");
//        Dashboard dashboard = new Dashboard(1, "Home Page", "Desktop", contact);
//        Widget widget = new Widget(10, "chart", 70, 70, dashboard, configs);
//
//        Mockito.when(dashboardRepository.findById(1)).thenReturn(Optional.of(dashboard));
//        Mockito.when(widgetRepository.save(widget)).thenReturn(widget);
//
//        WidgetDto actualWidgetDto = widgetService.save(expectedWidgetDto);
//
//        Assert.assertEquals(expectedWidgetDto.getMinHeight(), actualWidgetDto.getMinHeight());
//        Assert.assertEquals(expectedWidgetDto.getMinWidth(), actualWidgetDto.getMinWidth());
//        Assert.assertEquals(expectedWidgetDto.getType(), actualWidgetDto.getType());
//        Assert.assertEquals(expectedWidgetDto.getConfigs(), actualWidgetDto.getConfigs());
//    }

//    @Test
//    void saveTest_WhenDashboardIsNotFound_ThenReturnDashboardNotFoundException(){
//        Config typeWidgets = new Config(70, 70, 70, 70);
//        Map<String, Config> configs = new HashMap<>();
//        configs.put("type_widget", typeWidgets);
//        WidgetDto expectedWidgetDto = new WidgetDto(1, "chart", 70, 70, configs);
//
//        String message = "Dashboard with id: " + expectedWidgetDto.getDashboard_id()  +
//                " does not exist.";
//
//        Exception exception = assertThrows(DashboardNotFoundException.class, () ->{
//            Mockito.when(widgetService.save(expectedWidgetDto));});
//
//        Assert.assertEquals(message, exception.getMessage());
//    }
}