package com.myproject.task.userrestapi.dto.request;

import com.myproject.task.userrestapi.validation.annotation.DateRange;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

@DateRange
public class DateRangeReqDto {

    @NotNull(message = "FromDate is required")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate from;

    @NotNull(message = "ToDate is required")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate to;

}
