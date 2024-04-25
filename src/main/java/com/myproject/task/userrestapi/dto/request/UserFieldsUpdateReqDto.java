package com.myproject.task.userrestapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject.task.userrestapi.validation.annotation.MinAge;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFieldsUpdateReq {

    @Email(message = "E-mail have to be valid.")
    private String email;

    private String firstName;

    private String lastName;

    @MinAge
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
