package com.myproject.task.userrestapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myproject.task.userrestapi.dto.request.UserCreateReqDto;
import com.myproject.task.userrestapi.dto.request.UserFieldsUpdateReqDto;
import com.myproject.task.userrestapi.dto.request.UserUpdateReqDto;
import com.myproject.task.userrestapi.dto.response.UserResDto;
import com.myproject.task.userrestapi.entity.User;
import com.myproject.task.userrestapi.mapper.UserMapper;
import com.myproject.task.userrestapi.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Value("${api.prefix}")
    private String apiPrefix;



    private static final Long userId = 2512L;
    private static final User validUser = new User();
    private static final List<User> userList = List.of(validUser, new User());


    private static final LocalDate fromDate = LocalDate.of(2000, 1, 1);
    private static final LocalDate toDate = LocalDate.of(2005, 1, 1);


    private static final UserCreateReqDto validUserCreateReqDto = new UserCreateReqDto();
    private static final UserUpdateReqDto validUserUpdateReqDto = new UserUpdateReqDto();
    private static final UserFieldsUpdateReqDto validUserFieldsUpdateReqDto = new UserFieldsUpdateReqDto();



    @BeforeAll
    static void init() {

        validUser.setEmail("some@mail.com");
        validUser.setFirstName("fname");
        validUser.setLastName("lname");
        validUser.setBirthDate(LocalDate.of(2000, 1, 1));
        validUser.setAddress("someAddress");

        validUserCreateReqDto.setEmail(validUser.getEmail());
        validUserCreateReqDto.setFirstName(validUser.getFirstName());
        validUserCreateReqDto.setLastName(validUser.getLastName());
        validUserCreateReqDto.setBirthDate(validUser.getBirthDate());

        validUserUpdateReqDto.setEmail(validUser.getEmail());
        validUserUpdateReqDto.setFirstName(validUser.getFirstName());
        validUserUpdateReqDto.setLastName(validUser.getLastName());
        validUserUpdateReqDto.setBirthDate(validUser.getBirthDate());

        validUserFieldsUpdateReqDto.setAddress("newAddress");
        validUserFieldsUpdateReqDto.setEmail("new@email.com");
    }


    @Test
    void getUsersByBirthDateRange_shouldReturnOkStatus() throws Exception {

        when(userService.getUsersByBirthDateRange(fromDate, toDate)).thenReturn(userList);

        mvc.perform(get(apiPrefix + "/users/birthdate")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUsersByBirthDateRange_shouldReturnProperResponse() throws Exception {

        List<UserResDto> expectedUserDtoList = userList.stream().map(userMapper::mapToUserResDto).toList();

        when(userService.getUsersByBirthDateRange(fromDate, toDate)).thenReturn(userList);

        MvcResult mvcResult = mvc.perform(get(apiPrefix + "/users/birthdate")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<UserResDto> actualUserDtoList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});


        assertEquals(expectedUserDtoList, actualUserDtoList);

        verify(userService).getUsersByBirthDateRange(fromDate, toDate);
    }

    @Test
    void getUsersByBirthDateRange_shouldReturnBadRequest_ifDateRangeIsInvalid() throws Exception {

        mvc.perform(get(apiPrefix + "/users/birthdate")
                        .param("from", toDate.toString())
                        .param("to", fromDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createUser_shouldReturnCreatedStatus_ifUserIsValid() throws Exception {

        User userToCreate = userMapper.mapToUser(validUserCreateReqDto);

        when(userService.create(userToCreate)).thenReturn(validUser);

        mvc.perform(post(apiPrefix + "/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreateReqDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createUser_shouldReturnProperResponse_ifUserIsValid() throws Exception {

        User userToCreate = userMapper.mapToUser(validUserCreateReqDto);

        when(userService.create(userToCreate)).thenReturn(validUser);
        UserResDto expectedUserResDto = userMapper.mapToUserResDto(validUser);


        MvcResult mvcResult = mvc.perform(post(apiPrefix + "/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(validUserCreateReqDto)))
                .andReturn();

        UserResDto actualUserResDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});


        assertEquals(expectedUserResDto, actualUserResDto);

        verify(userService).create(userToCreate);
    }

    @Test
    void createUser_shouldReturnBadRequestStatus_ifUserIsInvalid() throws Exception {

        UserCreateReqDto invalidUserCreateReqDto = new UserCreateReqDto(
                "om", "", "lname", LocalDate.of(2000, 1, 1), "address", "number"
        );

        mvc.perform(post(apiPrefix + "/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserCreateReqDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void update_shouldReturnOkStatus_ifUserIsValid() throws Exception {

        User userToUpdate = userMapper.mapToUser(validUserUpdateReqDto);

        when(userService.update(userId, userToUpdate)).thenReturn(validUser);


        mvc.perform(put(apiPrefix + "/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserUpdateReqDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update_shouldReturnProperResponse_ifUserIsValid() throws Exception {

        User userToUpdate = userMapper.mapToUser(validUserUpdateReqDto);

        when(userService.update(userId, userToUpdate)).thenReturn(validUser);
        UserResDto expectedUserResDto = userMapper.mapToUserResDto(validUser);


        MvcResult mvcResult = mvc.perform(put(apiPrefix + "/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserUpdateReqDto)))
                .andReturn();

        UserResDto actualUserResDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});


        assertEquals(expectedUserResDto, actualUserResDto);

        verify(userService).update(userId, userToUpdate);
    }

    @Test
    void update_shouldReturnBadRequestStatus_ifUserIsInvalid() throws Exception {

        UserUpdateReqDto invalidUserUpdateReqDto = new UserUpdateReqDto(
                "m", "fname", "", LocalDate.of(2000, 1, 1), "address", "number"
        );

        mvc.perform(put(apiPrefix + "/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserUpdateReqDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void patch_shouldReturnOkStatus_ifUserFieldsUpdateReqIsValid() throws Exception {

        User userToUpdate = userMapper.mapToUser(validUserFieldsUpdateReqDto);

        when(userService.updatePresentFields(userId, userToUpdate)).thenReturn(validUser);


        mvc.perform(patch(apiPrefix + "/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserFieldsUpdateReqDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void patch_shouldReturnProperResponse_ifUserFieldsUpdateReqIsValid() throws Exception {

        User userToUpdate = userMapper.mapToUser(validUserFieldsUpdateReqDto);

        when(userService.updatePresentFields(userId, userToUpdate)).thenReturn(validUser);
        UserResDto expectedUserResDto = userMapper.mapToUserResDto(validUser);


        MvcResult mvcResult = mvc.perform(patch(apiPrefix + "/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserFieldsUpdateReqDto)))
                .andReturn();

        UserResDto actualUserResDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});


        assertEquals(expectedUserResDto, actualUserResDto);

        verify(userService).updatePresentFields(userId, userToUpdate);
    }

    @Test
    void patch_shouldReturnBadRequestStatus_ifUserFieldsUpdateReqIsInvalid() throws Exception {

        UserFieldsUpdateReqDto invalidUserFieldsUpdateReqDto = new UserFieldsUpdateReqDto();
        invalidUserFieldsUpdateReqDto.setEmail("invalidEmail");

        mvc.perform(patch(apiPrefix + "/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserFieldsUpdateReqDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    void delete_shouldReturnOkStatus() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + userId))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldProperlyDeleteUser() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + userId));

        verify(userService).delete(userId);
    }
}