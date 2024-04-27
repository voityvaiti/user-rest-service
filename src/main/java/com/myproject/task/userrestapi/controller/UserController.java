package com.myproject.task.userrestapi.controller;

import com.myproject.task.userrestapi.dto.request.DateRangeReqDto;
import com.myproject.task.userrestapi.dto.request.UserFieldsUpdateReqDto;
import com.myproject.task.userrestapi.dto.request.UserCreateReqDto;
import com.myproject.task.userrestapi.dto.request.UserUpdateReqDto;
import com.myproject.task.userrestapi.dto.response.UserResDto;
import com.myproject.task.userrestapi.entity.User;

import com.myproject.task.userrestapi.mapper.UserMapper;
import com.myproject.task.userrestapi.service.abstraction.UserService;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/birthdate")
    public ResponseEntity<List<UserResDto>> getUsersByBirthDateRange(@Valid DateRangeReqDto dateRangeReqDto) {

        List<User> users = userService.getUsersByBirthDateRange(
                dateRangeReqDto.getFrom(), dateRangeReqDto.getTo()
        );

        return ResponseEntity.ok(
                users.stream().map(userMapper::mapToUserResDto).toList()
        );
    }

    @PostMapping
    public ResponseEntity<UserResDto> create(@RequestBody @Valid UserCreateReqDto userCreateReqDto) {

        User createdUser = userService.create(userMapper.mapToUser(userCreateReqDto));

        return new ResponseEntity<>(userMapper.mapToUserResDto(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateReqDto userUpdateReqDto) {

        User updatedUser = userMapper.mapToUser(userUpdateReqDto);
        updatedUser = userService.update(id, updatedUser);

        return ResponseEntity.ok(
                userMapper.mapToUserResDto(updatedUser)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResDto> patch(@PathVariable Long id, @RequestBody @Valid UserFieldsUpdateReqDto fieldsUpdateReq) {

        User userWithUpdatedFields = userMapper.mapToUser(fieldsUpdateReq);
        User updatedUser = userService.updatePresentFields(id, userWithUpdatedFields);

        return ResponseEntity.ok(
                userMapper.mapToUserResDto(updatedUser)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }

}
