package com.myproject.task.userrestapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

    private String address;

    private String telNumber;

}
