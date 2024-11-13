package com.trionesdev.phecda.backend.rest.backend.domains.notification.controller.ro;

import lombok.Data;

@Data
public class ContactCreateRO {
    private String name;
    private String phone;
    private String email;
    private String remark;
}
