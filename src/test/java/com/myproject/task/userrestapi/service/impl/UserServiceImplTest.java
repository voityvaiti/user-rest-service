package com.myproject.task.userrestapi.service.impl;

import com.myproject.task.userrestapi.entity.User;
import com.myproject.task.userrestapi.exception.ResourceNotFoundException;
import com.myproject.task.userrestapi.repository.UserRepository;
import com.myproject.task.userrestapi.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;


    private static final Long id = 435L;
    private static final User sampleUser = new User();


    @BeforeAll
    static void init() {
        sampleUser.setEmail("mail@mail.com");
        sampleUser.setFirstName("Fname");
        sampleUser.setLastName("Lname");
        sampleUser.setBirthDate(LocalDate.of(2000, 1, 1));
        sampleUser.setTelNumber("someNumber");
    }


    @Test
    void getUsersByBirthDateRange_shouldReturnUserList() {

        List<User> userList = Arrays.asList(sampleUser, new User());

        LocalDate fromDate = LocalDate.of(2000, 7, 11);
        LocalDate toDate = LocalDate.of(2002, 7, 11);

        when(userRepository.findByBirthDateBetween(fromDate, toDate)).thenReturn(userList);

        assertEquals(userList, userService.getUsersByBirthDateRange(fromDate, toDate));

        verify(userRepository).findByBirthDateBetween(fromDate, toDate);
    }

    @Test
    void create_shouldSaveUser() {

        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        assertEquals(sampleUser, userService.create(sampleUser));

        verify(userRepository).save(sampleUser);
    }

    @Test
    void updateAllFields_shouldUpdateAllFields_ifUserWasFound() {

        User currentUser = new User();
        currentUser.setId(id);
        currentUser.setEmail("some@mail.com");
        currentUser.setFirstName("some-fname");

        User updatedUser = new User();
        updatedUser.setEmail("new@email.com");
        updatedUser.setFirstName("new-fname");

        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setEmail(updatedUser.getEmail());
        expectedUser.setFirstName(updatedUser.getFirstName());


        when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        assertEquals(expectedUser, userService.update(id, updatedUser));

        verify(userRepository).save(expectedUser);
    }

    @Test
    void updateAllFields_shouldThrowException_ifUserWasNotFound() {

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(id, sampleUser));

        verify(userRepository, never()).save(any());

    }

    @Test
    void updateSomeFields_shouldUpdatePresentFields_ifUserWasFound() {

        User currentUser = new User();
        currentUser.setId(id);
        currentUser.setEmail("some@email.com");
        currentUser.setFirstName("fname");

        User updatedUser = new User();
        updatedUser.setEmail("new@email.com");
        updatedUser.setTelNumber("telNumber");

        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setEmail(updatedUser.getEmail());
        expectedUser.setFirstName(currentUser.getFirstName());
        expectedUser.setTelNumber(updatedUser.getTelNumber());


        when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        assertEquals(expectedUser, userService.updatePresentFields(id, updatedUser));

        verify(userRepository).save(expectedUser);
    }

    @Test
    void updateSomeFields_shouldThrowException_ifUserWasNotFound() {

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updatePresentFields(id, sampleUser));

        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteUser_ifUserWasFound() {

        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));

        userService.delete(id);

        verify(userRepository).delete(sampleUser);
    }

    @Test
    void delete_shouldThrowException_ifUserWasNotFound() {

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.delete(id));

        verify(userRepository, never()).delete(any());
    }
}