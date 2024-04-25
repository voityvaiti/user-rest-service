package com.myproject.task.userrestapi.dto.request;

import com.myproject.task.userrestapi.validation.annotation.UserAgeConstraint;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFieldsUpdateReq {

    @Null
    @NotBlank(message = "E-mail can't be blank.")
    @Email(message = "E-mail have to be valid.")
    private String email;

    @Null
    @NotBlank(message = "First name can't be blank.")
    private String firstName;

    @Null
    @NotBlank(message = "Last name can't be blank.")
    private String lastName;

    @Null
    @UserAgeConstraint
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
