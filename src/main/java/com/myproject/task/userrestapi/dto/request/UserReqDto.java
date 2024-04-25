package com.myproject.task.userrestapi.dto.request;

import com.myproject.task.userrestapi.validation.annotation.UserAgeConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

    @NotEmpty(message = "E-mail is required.")
    @Email(message = "E-mail have to be valid.")
    private String email;

    @NotEmpty(message = "First name is required.")
    private String firstName;

    @NotEmpty(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Birth date is required.")
    @UserAgeConstraint
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
