package com.myproject.task.userrestapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject.task.userrestapi.validation.annotation.MinAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

    @NotBlank(message = "E-mail is required.")
    @Email(message = "E-mail have to be valid.")
    private String email;

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Birth date is required.")
    @MinAge
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
