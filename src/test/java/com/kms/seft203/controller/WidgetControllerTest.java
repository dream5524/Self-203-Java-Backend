package com.kms.seft203.controller;

import com.kms.seft203.dto.WidgetDto;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.service.WidgetService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;

@WebMvcTest(WidgetApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class WidgetControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WidgetService widgetService;

//    @Test
//    void saveTest_WhenDashboardIsFound_ThenReturnMessageSuccessfully() throws Exception {
//        String message = "Widget was created successfully !";
//        Config typeWidgets = new Config(70, 70, 70, 70);
//        Map<String, Config> configs = new HashMap<>();
//        configs.put("type_widget", typeWidgets);
//        WidgetDto widgetDto = new WidgetDto(1, "chart", 70, 70, configs);
//
//        Mockito.when(widgetService.save(widgetDto)).thenReturn(widgetDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/widget")
//                .content(convertObjectToJsonString(widgetDto))
//                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
//                .andExpect(content().string(message));
//    }

//    @Test
//    void saveTest_WhenDashboardIsNotFound_ThenReturnStatusNotFound() throws Exception {
//        Config typeWidgets = new Config(70, 70, 70, 70);
//        Map<String, Config> configs = new HashMap<>();
//        configs.put("type_widget", typeWidgets);
//        WidgetDto widgetDto = new WidgetDto(1, "chart", 70, 70, configs);
//        String message = "Dashboard with id: " + widgetDto.getDashboard_id()  +
//                " does not exist.";
//
//        Mockito.when(widgetService.save(widgetDto)).thenThrow(new DashboardNotFoundException(message));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/widget")
//                .content(convertObjectToJsonString(widgetDto))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.messages[0]").value(message));
//    }
}
