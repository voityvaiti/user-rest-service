package com.myproject.task.userrestapi.service.impl;

import com.myproject.task.userrestapi.entity.User;
import com.myproject.task.userrestapi.exception.DateRangeException;
import com.myproject.task.userrestapi.exception.ResourceNotFoundException;
import com.myproject.task.userrestapi.repository.UserRepository;
import com.myproject.task.userrestapi.service.abstraction.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String NOT_FOUND_BY_ID_MESSAGE = "Not found User by ID: ";

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {

        if (fromDate.isAfter(toDate)) {
            throw new DateRangeException("TO date is less than FROM.");
        }

        return userRepository.findByBirthDateBetween(fromDate, toDate);
    }

    @Override
    public User add(User user) {

        return userRepository.save(user);
    }

    @Override
    public User updateAllFields(Long id, User updatedUser) {
        Optional<User> optionalUserToUpdate = userRepository.findById(id);

        if (optionalUserToUpdate.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_BY_ID_MESSAGE + id);
        }

        User userToUpdate = optionalUserToUpdate.get();

        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setBirthDate(updatedUser.getBirthDate());
        userToUpdate.setAddress(updatedUser.getAddress());
        userToUpdate.setTelNumber(updatedUser.getTelNumber());

        return userRepository.save(userToUpdate);

    }

    @Override
    public User updateSomeFields(Long id, User updatedUser) {

        Optional<User> optionalUserToUpdate = userRepository.findById(id);

        if (optionalUserToUpdate.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_BY_ID_MESSAGE + id);
        }

        User userToUpdate = optionalUserToUpdate.get();

        if (StringUtils.isNotBlank(updatedUser.getEmail())) {
            userToUpdate.setEmail(updatedUser.getEmail());
        }

        if (StringUtils.isNotBlank(updatedUser.getFirstName())) {
            userToUpdate.setFirstName(updatedUser.getFirstName());
        }

        if (StringUtils.isNotBlank(updatedUser.getLastName())) {
            userToUpdate.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getBirthDate() != null) {
            userToUpdate.setBirthDate(updatedUser.getBirthDate());
        }

        if (StringUtils.isNotBlank(updatedUser.getAddress())) {
            userToUpdate.setAddress(updatedUser.getAddress());
        }

        if (StringUtils.isNotBlank(updatedUser.getTelNumber())) {
            userToUpdate.setTelNumber(updatedUser.getTelNumber());
        }

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {

        Optional<User> optionalUserToUpdate = userRepository.findById(id);

        if (optionalUserToUpdate.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_BY_ID_MESSAGE + id);
        }

        userRepository.deleteById(id);
    }

}
