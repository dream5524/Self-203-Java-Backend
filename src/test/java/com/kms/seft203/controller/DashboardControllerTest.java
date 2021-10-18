package com.kms.seft203.controller;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.service.DashboardService;
import javassist.tools.web.BadHttpRequest;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
class DashboardControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DashboardService dashboardService;

    @Test
    void testCreateDashboard_whenSuccess_thenReturnDashboardDto() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String title = "Home page";
        String layoutType = "Dark mode";
        DashboardCreateDto dashboardCreateDto = new DashboardCreateDto(email, title, layoutType);
        DashboardResponseDto dashboardResponseDto = new DashboardResponseDto(title, layoutType);
        Mockito.when(dashboardService.save(dashboardCreateDto)).thenReturn(dashboardResponseDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                        .content(convertObjectToJsonString(dashboardCreateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.layoutType").value(layoutType));
    }

    @Test
    void testCreateDashboard_whenDashboardDuplicated_thenReturnBadHttpRequest() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String title = "Home page";
        String layoutType = "Dark mode";
        DashboardCreateDto dashboardDto = new DashboardCreateDto(email, title, layoutType);
        Mockito.when(dashboardService.save(dashboardDto)).thenThrow(
                new BadHttpRequest(new Exception("Dashboard exists, pls change method to put"))
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                        .content(convertObjectToJsonString(dashboardDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateDashboard_whenContactNotFound_thenReturnContactNotFoundException() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String title = "Home page";
        String layoutType = "Dark mode";
        String message = "Contact of user " + email + " not found!";
        DashboardCreateDto dashboardDto = new DashboardCreateDto(email, title, layoutType);
        Mockito.when(dashboardService.save(dashboardDto)).thenThrow(
                new ContactNotFoundException(message)
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                        .content(convertObjectToJsonString(dashboardDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void getAllDashboardsTest_WhenSuccess_ThenReturnListDashboardDto() throws Exception {
        List<DashboardResponseDto> dashboardResponseDtoList = Stream.of(
                new DashboardResponseDto("Home Page", "Desktop"),
                new DashboardResponseDto("Internship Assignment", "Desktop")
        ).collect(Collectors.toList());

        Mockito.when(dashboardService.getAllDashboards()).thenReturn(dashboardResponseDtoList);

         mockMvc.perform(MockMvcRequestBuilders.get("/dashboards")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Home Page"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].layoutType").value("Desktop"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Internship Assignment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].layoutType").value("Desktop"));;
    }
}