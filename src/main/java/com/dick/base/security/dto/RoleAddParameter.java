package com.dick.base.security.dto;

import com.dick.base.util.ValidateMessageCodes;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RoleAddParameter {

    @NotEmpty(message = ValidateMessageCodes.Role_Name_NotEmpty)
    @Size(min = 1, max = 32, message = ValidateMessageCodes.Role_Name_Size)
    private String name;
    @NotEmpty(message = ValidateMessageCodes.Role_RoleCode_NotEmpty)
    @Size(min = 1, max = 32, message = ValidateMessageCodes.Role_RoleCode_Size)
    private String roleCode;

}
