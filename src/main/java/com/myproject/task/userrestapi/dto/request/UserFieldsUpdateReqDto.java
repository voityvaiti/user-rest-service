package com.myproject.task.userrestapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject.task.userrestapi.validation.annotation.NotBlankIfNotNull;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFieldsUpdateReqDto {

    @NotBlankIfNotNull
    @Email(message = "E-mail have to be valid.")
    private String email;

    @NotBlankIfNotNull
    private String firstName;

    @NotBlankIfNotNull
    private String lastName;

    @Past(message = "Birth date must be in the past.")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
