package com.kms.seft203.service;

import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardRequestDto;
import com.kms.seft203.dto.DashboardUpdateDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Dashboard;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardDuplicatedException;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.DashboardRepository;
import javassist.tools.web.BadHttpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DashboardServiceImp implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * This function is implemented to save a DashboardRequest to database
     *
     * @param dashboardDto
     * @return DashboardResponse
     * @throws ContactNotFoundException
     * @throws BadHttpRequest
     */
    @Override
    public DashboardRequestDto save(DashboardCreateDto dashboardDto) throws ContactNotFoundException, DashboardDuplicatedException {
        String email = dashboardDto.getEmail();
        Optional<Contact> contactOptional = contactRepository.findByEmail(email);
        if (contactOptional.isEmpty()) {
            throw new ContactNotFoundException(
                    "Contact not found exception! No contact found for user " + email + "!");
        }
        Contact contact = contactOptional.get();
        Optional<Dashboard> dashboardFromDb = dashboardRepository.findByContact(contact);
        if (dashboardFromDb.isPresent()) {
            throw new DashboardDuplicatedException("Dashboard of user " + email
                    + " does exist, pls change method to PUT to update the current dashboard");
        }
        Dashboard dashboard = modelMapper.map(dashboardDto, Dashboard.class);
        dashboard.setContact(contact);
        Dashboard savedDashboard = dashboardRepository.save(dashboard);
        return modelMapper.map(savedDashboard, DashboardRequestDto.class);
    }

    @Override
    public List<DashboardRequestDto> getAllDashboards() {
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        return dashboardList.stream().map(dashboard -> modelMapper.map(dashboard, DashboardRequestDto.class))
                .collect(Collectors.toList());
    }
    /**
     * This function is defined to update a DashboardUpdateDto to database
     *  @param dashboardUpdateDto
     * If it finds a specific dashboard by id, it will return:
     * @return DashboardUpdateDto
     * Otherwise, it throws an exception:
     * @throws DashboardNotFoundException
     */
    @Override
    public DashboardUpdateDto updateById(DashboardUpdateDto dashboardUpdateDto) throws DashboardNotFoundException {
        Optional<Dashboard> dashboardOptional = dashboardRepository.findById(dashboardUpdateDto.getId());
        if (dashboardOptional.isEmpty()) {
            throw new DashboardNotFoundException("There is no dashboard with the given id: " + dashboardUpdateDto.getId());
        }
        Dashboard dashboard = dashboardOptional.get();
        dashboard.setTitle(dashboardUpdateDto.getTitle());
        dashboard.setLayoutType(dashboardUpdateDto.getLayoutType());
        Dashboard saveDashboard = dashboardRepository.save(dashboard);
        return modelMapper.map(saveDashboard, DashboardUpdateDto.class);
    }
}
