package com.kms.seft203.controller;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.dto.DashboardUpdateDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardDuplicatedException;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        DashboardCreateDto dashboardCreateDto = new DashboardCreateDto(title, layoutType, email);
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

        String message = "Dashboard exists! Pls change method to put";
        DashboardCreateDto dashboardCreateDto = new DashboardCreateDto(title, layoutType, email);

        Mockito.when(dashboardService.save(dashboardCreateDto)).thenThrow(new DashboardDuplicatedException(message));

        mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                        .content(convertObjectToJsonString(dashboardCreateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]").value(message));
    }

    @Test
    void testCreateDashboard_whenContactNotFound_thenReturnContactNotFoundException() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String title = "Home page";
        String layoutType = "Dark mode";
        String message = "Contact of user " + email + " not found!";
        DashboardCreateDto dashboardDto = new DashboardCreateDto(title, layoutType, email);
        Mockito.when(dashboardService.save(dashboardDto)).thenThrow(
                new ContactNotFoundException(message)
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                        .content(convertObjectToJsonString(dashboardDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages[0]").value(message));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ab@ab.com,home-page,dark-mode",
            "duclocdk1999,,dark-mode",
            "duclocdk1999@gmail.com,home-page,",
            "duclocdk1999@gmail.com,,",
            ",home-page,dark-mode",
            ",home-page,",
            ",,dark-mode",
            ",,"
    }, delimiter = ',')
    void testCreateDashboard_whenInputIsInvalid_thenReturnStatusBadRequest(String email, String title, String layoutType) throws Exception {
        DashboardCreateDto dashboardCreateDto = new DashboardCreateDto(title, layoutType, email);

        mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                        .content(convertObjectToJsonString(dashboardCreateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].layoutType").value("Desktop"));
    }

    @Test
    void updateDashboardByIdTest_WhenSuccess_ThenReturnStatusOk() throws Exception {
        DashboardUpdateDto dashboardUpdateDto = new DashboardUpdateDto("IT Operation", "Desktop", 1);
        Mockito.when(dashboardService.updateById(dashboardUpdateDto)).thenReturn(dashboardUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/dashboards")
                        .content(convertObjectToJsonString(dashboardUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateDashboardByIdTest_whenDashboardNotFound_thenReturnStatusNotFound() throws Exception {
        Integer id = 1;
        String title = "IT Operation";
        String layoutType = "Desktop";
        String message = "There is no dashboard with the given id: " + id;
        DashboardUpdateDto dashboardUpdateDto = new DashboardUpdateDto(title, layoutType, id);
        Mockito.when(dashboardService.updateById(dashboardUpdateDto)).thenThrow(
                new DashboardNotFoundException(message));

        mockMvc.perform(MockMvcRequestBuilders.put("/dashboards")
                        .content(convertObjectToJsonString(dashboardUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages[0]").value(message));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "IT Operation,Desktop,",
            "IT,Desktop,1",
            "IT Operation,De,1",
            "IT,Desktop,",
            "IT Operation,De,",
            "IT,De,1",
            ",,"
    }, delimiter = ',')
    void updateDashboardByIdTest_WhenFieldsAreInValid_ThenReturnStatusBadRequest(String title, String layoutType, Integer id) throws Exception {
        DashboardUpdateDto dashboardUpdateDto = new DashboardUpdateDto(title, layoutType, id);

        mockMvc.perform(MockMvcRequestBuilders.put("/dashboards")
                        .content(convertObjectToJsonString(dashboardUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}