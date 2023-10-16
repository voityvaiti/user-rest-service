package com.myproject.task.userrestapi.controller;

import com.myproject.task.userrestapi.entity.User;

import com.myproject.task.userrestapi.service.abstraction.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/birthdate/{fromDate}/{toDate}")
    public ResponseEntity<List<User>> searchUsersByBirthDateRange(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fromDate,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate toDate) {

        List<User> users = userService.getUsersByBirthDateRange(fromDate, toDate);

        return ResponseEntity.ok(users);
    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));

            throw new ValidationException(errorMessage);
        }

        User createdUser = userService.add(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateAllFields(
            @PathVariable Long id,
            @RequestBody @Valid User updatedUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));

            throw new ValidationException(errorMessage);
        }

        updatedUser = userService.updateAllFields(id, updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateSomeFields(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        updatedUser = userService.updateSomeFields(id, updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Long id) {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }

}
