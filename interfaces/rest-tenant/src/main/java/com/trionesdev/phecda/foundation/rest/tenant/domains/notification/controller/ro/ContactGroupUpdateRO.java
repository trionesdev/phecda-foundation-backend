package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro;

import lombok.Data;

import java.util.List;

@Data
public class ContactGroupUpdateRO {
    private String name;
    private String description;
    private List<String> contactIds;
}
