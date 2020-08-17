package com.dick.base.session.dto;

import com.dick.base.util.ValidateMessageCodes;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignUpParameter {

    @NotEmpty(message = ValidateMessageCodes.Username_NotEmpty)
    @Size(min = 6, max =32, message = ValidateMessageCodes.Username_Size)
    private String username;
    @NotEmpty(message = ValidateMessageCodes.Password_NotEmpty)
    @Size(min = 6, max =32, message = ValidateMessageCodes.Password_Size)
    private String password;
}
