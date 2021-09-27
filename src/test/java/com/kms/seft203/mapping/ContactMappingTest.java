package com.kms.seft203.mapping;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.entity.Contact;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
/*
* This class is defined for checking mapping between
*  source(Contact.class) and destination(ContactRequestDTO.class)
* */
public class ContactMappingTest {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkMappingContact() {
        ContactRequestDTO contactReq = new ContactRequestDTO();
        contactReq.setFirstName("Huyen");
        contactReq.setLastName("Mo");
        contactReq.setTitle("demo thu nhA");
        contactReq.setProject("demo project");

        Contact contact = modelMapper.map(contactReq, Contact.class);
        assertEquals(contactReq.getFirstName(), contact.getFirstName());
        assertEquals(contactReq.getLastName(), contact.getLastName());
        assertEquals(contactReq.getTitle(), contact.getTitle());
        assertEquals(contactReq.getProject(), contact.getProject());
        assertEquals(contactReq.getDateCreated(), contact.getDateCreated());

    }
}