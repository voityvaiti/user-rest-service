package com.myproject.task.userrestapi.service.abstraction;

import com.myproject.task.userrestapi.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);

    User add(User user);

    User updateAllFields(Long id, User updatedUser);

    User updateSomeFields(Long id, User updtedUser);

    void delete(Long id);

}
