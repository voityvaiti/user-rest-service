package com.myproject.task.userrestapi.controller;

import com.myproject.task.userrestapi.entity.User;
import com.myproject.task.userrestapi.service.abstraction.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void searchUsersByBirthDateRange() throws Exception {
        String fromDateStr = "10-05-2000";
        String toDateStr = "17-10-2002";

        LocalDate fromDate = LocalDate.of(2000, 5, 10);
        LocalDate toDate = LocalDate.of(2002, 10, 17);

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User());
        mockUsers.add(new User());

        when(userService.getUsersByBirthDateRange(fromDate, toDate)).thenReturn(mockUsers);

        mvc.perform(get("/api/v1/users/search")
                        .param("fromDate", fromDateStr)
                        .param("toDate", toDateStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void create_shouldCreateUser_ifUserFieldsAreValid() throws Exception {
        User expectedUser = new User();
        expectedUser.setEmail("mail@mail.com");
        expectedUser.setFirstName("Fname");
        expectedUser.setLastName("Lname");
        expectedUser.setBirthDate(LocalDate.of(2000, 5, 5));


        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "mail@mail.com",
                            "firstName": "Fname",
                            "lastName": "Lname",
                            "birthDate": "05-05-2000"
                        }
                        """)
        ).andExpect(status().isCreated());

        verify(userService).add(expectedUser);
    }

    @Test
    void create_shouldReturnError_ifUserFieldsAreInvalid() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "mail",
                            "firstName": "Fname",
                            "birthDate": "05-05-2010"
                        }
                        """)
        ).andExpect(status().isBadRequest());

        verify(userService, never()).add(any());
    }


    @Test
    void updateAllFields_shouldUpdateUser_ifUserFieldsAreValid() throws Exception {
        Long id = 10L;
        User expectedUser = new User();
        expectedUser.setEmail("mail@mail.com");
        expectedUser.setFirstName("Fname");
        expectedUser.setLastName("Lname");
        expectedUser.setBirthDate(LocalDate.of(2000, 5, 5));


        mvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "mail@mail.com",
                            "firstName": "Fname",
                            "lastName": "Lname",
                            "birthDate": "05-05-2000"
                        }
                        """)
        ).andExpect(status().isOk());

        verify(userService).updateAllFields(id, expectedUser);
    }

    @Test
    void updateAllFields_shouldNotUpdateUser_ifUserFieldsAreInvalid() throws Exception {
        Long id = 10L;

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "mail",
                            "firstName": "Fname",
                            "lastName": "Lname",
                            "birthDate": "05-05-2010"
                        }
                        """)
        ).andExpect(status().isBadRequest());

        verify(userService, never()).updateAllFields(any(), any());

    }

    @Test
    void updateSomeFields() throws Exception {
        Long id = 7L;

        User expectedUser = new User();
        expectedUser.setEmail("mail@mail.com");
        expectedUser.setFirstName("Fname");
        expectedUser.setBirthDate(LocalDate.of(2000, 5, 5));
        expectedUser.setAddress("someaddress");

        mvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "mail@mail.com",
                            "firstName": "Fname",
                            "birthDate": "05-05-2000",
                            "address": "someaddress"
                        }
                        """)
        ).andExpect(status().isOk());

        verify(userService).updateSomeFields(id, expectedUser);
    }

    @Test
    void delete() throws Exception {
        Long id = 5L;

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + id))
                .andExpect(status().isOk());

        verify(userService).delete(id);
    }
}