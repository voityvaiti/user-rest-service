package com.myproject.task.userrestapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeReqDto {

    @NotBlank(message = "FromDate is required")
    private LocalDate from;

    @NotBlank(message = "ToDate is required")
    private LocalDate to;

}
