package com.trionesdev.phecda.foundation.core.domains.org.dto;

import lombok.Data;

@Data
public class ChangePasswordCmd {
    private String id;
    private String password;
}
