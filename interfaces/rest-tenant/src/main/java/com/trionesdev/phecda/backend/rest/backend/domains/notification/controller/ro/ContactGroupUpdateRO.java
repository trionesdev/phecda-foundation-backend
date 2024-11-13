package com.trionesdev.phecda.backend.rest.backend.domains.notification.controller.ro;

import lombok.Data;

import java.util.List;

@Data
public class ContactGroupUpdateRO {
    private String name;
    private String description;
    private List<String> contactIds;
}
