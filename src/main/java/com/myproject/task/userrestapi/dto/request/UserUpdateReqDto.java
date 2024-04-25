package com.myproject.task.userrestapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject.task.userrestapi.validation.annotation.user.BirthDateConstraints;
import com.myproject.task.userrestapi.validation.annotation.user.EmailConstraints;
import com.myproject.task.userrestapi.validation.annotation.user.FirstNameConstraints;
import com.myproject.task.userrestapi.validation.annotation.user.LastNameConstraints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateReqDto {

    @EmailConstraints
    private String email;

    @FirstNameConstraints
    private String firstName;

    @LastNameConstraints
    private String lastName;

    @BirthDateConstraints
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
