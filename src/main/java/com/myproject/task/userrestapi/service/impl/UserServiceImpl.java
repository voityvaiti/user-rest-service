package com.myproject.task.userrestapi.service.impl;

import com.myproject.task.userrestapi.entity.User;
import com.myproject.task.userrestapi.exception.ResourceNotFoundException;
import com.myproject.task.userrestapi.mapper.UserMapper;
import com.myproject.task.userrestapi.repository.UserRepository;
import com.myproject.task.userrestapi.service.abstraction.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String NOT_FOUND_BY_ID_MESSAGE = "Not found User by ID: ";

    private final UserRepository userRepository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);



    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {

        log.debug("Looking for users between {} and {} birth date.", fromDate, toDate);

        return userRepository.findByBirthDateBetween(fromDate, toDate);
    }

    @Override
    public User create(User user) {

        log.debug("Saving User: {}", user);

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User updatedUser) {

        log.debug("Looking for User with ID: {} to update: {}", id, updatedUser);
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BY_ID_MESSAGE + id));

        log.debug("Found User to update: {}", userToUpdate);

        userToUpdate = userMapper.updateFields(updatedUser, userToUpdate);

        log.debug("Saving updated User: {}", userToUpdate);
        return userRepository.save(userToUpdate);
    }

    @Override
    public User updatePresentFields(Long id, User updatedUser) {

        log.debug("Looking for User with ID: {} to update some fields: {}", id, updatedUser);
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BY_ID_MESSAGE + id));

        log.debug("Found User to update some fields: {}", userToUpdate);

        userToUpdate = userMapper.updateFieldsIfPresent(updatedUser, userToUpdate);

        log.debug("Saving updated User: {}", userToUpdate);
        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {

        log.debug("Looking for User to delete with ID: {}", id);

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BY_ID_MESSAGE + id));

        log.info("Removing User: {}", userToDelete);
        userRepository.delete(userToDelete);
    }

}
