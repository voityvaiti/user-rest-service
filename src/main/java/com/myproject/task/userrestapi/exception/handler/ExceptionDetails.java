package com.myproject.task.userrestapi.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionDetails {

    private LocalDateTime timestamp;

    private String message;

}
