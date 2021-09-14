package com.kms.seft203.dto;

import com.kms.seft203.entity.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaveContactRequest extends Contact {
}
