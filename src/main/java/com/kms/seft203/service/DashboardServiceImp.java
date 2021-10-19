package com.kms.seft203.service;


import com.kms.seft203.dto.DashboardCreateDto;
import com.kms.seft203.dto.DashboardResponseDto;
import com.kms.seft203.dto.DashboardUpdateDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Dashboard;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.DashboardRepository;
import javassist.tools.web.BadHttpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service @Transactional
public class DashboardServiceImp implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * This function is implemented to save a DashboardRequest to database
     * @param dashboardDto
     * @return DashboardResponse
     * @throws ContactNotFoundException
     * @throws BadHttpRequest
     */
    @Override
    public DashboardDto save(DashboardDto dashboardDto) throws ContactNotFoundException, BadHttpRequest {
        String email = dashboardDto.getEmail();
        Optional<Contact> contactOptional = contactRepository.findByEmail(email);
        if (contactOptional.isEmpty()) {
            throw new ContactNotFoundException(
                    "Contact not found exception! No contact found for user " + email + "!");
        }
        Contact contact = contactOptional.get();
        Optional<Dashboard> dashboardFromDb = dashboardRepository.findByContact(contact);
        if (dashboardFromDb.isPresent()) {
            throw new BadHttpRequest(new Exception("Dashboard of user " + email +
                    " does exist, pls change method to PUT to update the current dashboard"
            ));
        }
        Dashboard dashboard = modelMapper.map(dashboardDto, Dashboard.class);
        dashboard.setContact(contact);
        Dashboard savedDashboard = dashboardRepository.save(dashboard);
        return modelMapper.map(savedDashboard, DashboardDto.class);
    }

    @Override
    public DashboardUpdateDto updateById(DashboardUpdateDto newDashboard) throws DashboardNotFoundException {
       Dashboard dashboardFromDb = dashboardRepository.findById(newDashboard.getId()).get();
        dashboardFromDb.setTitle(newDashboard.getTitle());
        dashboardFromDb.setLayoutType(newDashboard.getLayoutType());
        Dashboard saveDashboard = dashboardRepository.save(dashboardFromDb);
        return modelMapper.map(saveDashboard, DashboardUpdateDto.class);

    }

}
