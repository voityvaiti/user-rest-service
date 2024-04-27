package com.myproject.task.userrestapi.service.abstraction;

import com.myproject.task.userrestapi.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);

    User create(User user);

    User update(Long id, User updatedUser);

    User updatePresentFields(Long id, User updatedUser);

    void delete(Long id);

}
