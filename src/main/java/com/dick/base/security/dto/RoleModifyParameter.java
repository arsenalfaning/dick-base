package com.dick.base.security.dto;

import com.dick.base.util.ValidateMessageCodes;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleModifyParameter extends RoleAddParameter{

    @NotNull(message = ValidateMessageCodes.Role_Id_NotEmpty)
    private Integer id;
}
