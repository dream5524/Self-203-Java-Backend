package com.kms.seft203.service;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.dto.DashboardUpdateDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Dashboard;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.DashboardRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
class DashboardServiceTest {
    @Autowired
    private DashboardService dashboardService;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private DashboardRepository dashboardRepository;

    @Test
    void testSave_whenSuccess_thenReturnDashboardDto() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String title = "Login page";
        String layoutType = "Light mode";
        String firstName = "Loc";
        String lastName = "Nguyen";
        String project = "Healthcare";
        String password = "11Qaz123@@";
        DashboardCreateDto mockDashboardDto = new DashboardCreateDto(title, layoutType, email);
        User mockUser = new User(null, email, password, firstName + " " + lastName);
        Contact mockContact = new Contact(firstName, lastName, mockUser, title, project);
        Dashboard mockDashboard = new Dashboard(null, title, layoutType, mockContact);

        Dashboard dashboardFromDb = null;
        Mockito.when(contactRepository.findByEmail(email)).thenReturn(Optional.of(mockContact));
        Mockito.when(dashboardRepository.save(Mockito.any())).thenReturn(mockDashboard);
        Mockito.when(dashboardRepository.findByContact(mockContact)).thenReturn(Optional.ofNullable(dashboardFromDb));

        DashboardResponseDto dashboardResponseDto = dashboardService.save(mockDashboardDto);
        assertEquals(mockDashboardDto.getTitle(), dashboardResponseDto.getTitle());
        assertEquals(mockDashboardDto.getLayoutType(), dashboardResponseDto.getLayoutType());
    }

    @Test
    void getAllDashboardsTest_WhenSuccess_ThenReturnListDashboardDto() {
        List<Dashboard> expectedDashboardList = Stream.of(new Dashboard(1, "Home Page", "Desktop",
                new Contact("Huyen", "Mo", new User(1, "huyenmo@gmail.com",
                        "1qaz@@123QA", "Huyen Mo"), "Tester", "Build Dashboard"))
        ).collect(Collectors.toList());

        Mockito.when(dashboardRepository.findAll()).thenReturn(expectedDashboardList);

        List<DashboardResponseDto> actualDashboardResponseDtoList = dashboardService.getAllDashboards();

        Assert.assertEquals(1, actualDashboardResponseDtoList.stream().count());
        Assert.assertEquals(expectedDashboardList.get(0).getTitle(), actualDashboardResponseDtoList.get(0).getTitle());
        Assert.assertEquals(expectedDashboardList.get(0).getLayoutType(), actualDashboardResponseDtoList.get(0).getLayoutType());
    }

    @Test
    void updateByIdTest_WhenSuccess_ThenReturnDashboardUpdateDto() throws DashboardNotFoundException {
        User user = new User(1, "mohuyen@gmail.com", "1QAZa@@123", "Huyen Mo");
        Contact contact = new Contact("Huyen", "Mo", user, "Tester", "Build Dashboard");
        Dashboard dashboardFromDb = new Dashboard(1, "IT Operation", "Desktop", contact);

        Mockito.when(dashboardRepository.findById(dashboardFromDb.getId())).thenReturn(Optional.of(dashboardFromDb));
        dashboardFromDb.setTitle("Internship Assignment");
        dashboardFromDb.setLayoutType("Tablet");
        Mockito.when(dashboardRepository.save(dashboardFromDb)).thenReturn(dashboardFromDb);

        DashboardUpdateDto dashboardUpdateDto = new DashboardUpdateDto("Internship Assignment", "Tablet", 1);

        DashboardUpdateDto actualDashboardUpdateDto = dashboardService.updateById(dashboardUpdateDto);

        Assert.assertEquals(dashboardFromDb.getTitle(), actualDashboardUpdateDto.getTitle());
        Assert.assertEquals(dashboardFromDb.getLayoutType(), actualDashboardUpdateDto.getLayoutType());
    }
}
