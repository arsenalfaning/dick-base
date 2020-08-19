package com.dick.base.security.dto;

import com.dick.base.util.ValidateMessageCodes;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AuthorityAddParameter {

    @Size(min = 1, max = 32, message = ValidateMessageCodes.Authority_Name_Size)
    @NotEmpty(message = ValidateMessageCodes.Authority_Name_NotEmpty)
    private String name;

    @Min(value = 0, message = ValidateMessageCodes.Authority_AuthorityType_Range)
    @Max(value = 2, message = ValidateMessageCodes.Authority_AuthorityType_Range)
    private Byte authorityType;

    private Long parentId;
}
