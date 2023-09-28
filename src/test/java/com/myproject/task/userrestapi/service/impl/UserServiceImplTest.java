package com.myproject.task.userrestapi.service.impl;

import com.myproject.task.userrestapi.entity.User;
import com.myproject.task.userrestapi.exception.DateRangeException;
import com.myproject.task.userrestapi.exception.ResourceNotFoundException;
import com.myproject.task.userrestapi.repository.UserRepository;
import com.myproject.task.userrestapi.service.abstraction.UserService;
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


    @Test
    void getUsersByBirthDateRange_shouldUserList_ifParamsAreValid() {
        List<User> userList = Arrays.asList(new User(), new User());

        LocalDate fromDate = LocalDate.of(2000, 7, 11);
        LocalDate toDate = LocalDate.of(2002, 7, 11);

        when(userRepository.findByBirthDateBetween(fromDate, toDate)).thenReturn(userList);

        assertEquals(userList, userService.getUsersByBirthDateRange(fromDate, toDate));

        verify(userRepository).findByBirthDateBetween(fromDate, toDate);
    }

    @Test
    void getUsersByBirthDateRange_shouldThrowException_ifParamsAreInvalid() {

        List<User> userList = Arrays.asList(new User(), new User());

        LocalDate fromDate = LocalDate.of(2002, 7, 11);
        LocalDate toDate = LocalDate.of(2000, 7, 11);

        when(userRepository.findByBirthDateBetween(fromDate, toDate)).thenReturn(userList);

        assertThrows(DateRangeException.class, () -> userService.getUsersByBirthDateRange(fromDate, toDate));

        verify(userRepository, never()).findByBirthDateBetween(fromDate, toDate);

    }

    @Test
    void add() {
        User expectedUser = new User();
        expectedUser.setEmail("mail@mail.com");
        expectedUser.setFirstName("Fname");
        expectedUser.setLastName("Lname");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        assertEquals(expectedUser, userService.add(expectedUser));

        verify(userRepository).save(expectedUser);
    }

    @Test
    void updateAllFields_shouldUpdateAllFields_ifUserWasFound() {
        Long id = 5L;

        User currentUser = new User(id, "old email", "fname", "old lname",
                LocalDate.of(2000, 5, 10), "old address", "telNum");

        User updatedUser = new User(id, "new email", "fname", "new lname",
                LocalDate.of(2001, 5, 5), "new address", "telNum");

        when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        assertEquals(updatedUser, userService.updateAllFields(id, updatedUser));

        verify(userRepository).save(updatedUser);
    }

    @Test
    void updateAllFields_shouldThrowException_ifUserWasNotFound() {
        Long id = 5L;

        User updatedUser = new User(id, "new email", "fname", "new lname",
                LocalDate.of(2001, 5, 5), "new address", "telNum");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateAllFields(id, updatedUser));

        verify(userRepository, never()).save(any());

    }

    @Test
    void updateSomeFields_shouldUpdatePresentFields_ifUserWasFound() {
        Long id = 5L;

        User currentUser = new User(id, "old email", "fname", "old lname",
                LocalDate.of(2001, 5, 5), "old address", "telNum");

        User updatedUser = new User(null, "new email", null, "new lname",
                null, "new address", null);

        User expectedUser = new User(id, "new email", "fname", "new lname",
                LocalDate.of(2001, 5, 5), "new address", "telNum");

        when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        assertEquals(expectedUser, userService.updateSomeFields(id, updatedUser));

        verify(userRepository).save(expectedUser);
    }

    @Test
    void updateSomeFields_shouldThrowException_ifUserWasNotFound() {
        Long id = 5L;

        User updatedUser = new User(null, "new email", null, "new lname",
                null, "new address", null);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateSomeFields(id, updatedUser));

        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteUser_ifUserWasFound() {
        Long id = 16L;

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void delete_shouldThrowException_ifUserWasNotFound() {
        Long id = 16L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.delete(id));

        verify(userRepository, never()).deleteById(any());
    }
}