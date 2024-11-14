package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro;

import lombok.Data;

@Data
public class ContactCreateRO {
    private String name;
    private String phone;
    private String email;
    private String remark;
}
