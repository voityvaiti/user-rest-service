package com.myproject.task.userrestapi.mapper;

import com.myproject.task.userrestapi.dto.request.UserFieldsUpdateReqDto;
import com.myproject.task.userrestapi.dto.request.UserCreateReqDto;
import com.myproject.task.userrestapi.dto.request.UserUpdateReqDto;
import com.myproject.task.userrestapi.dto.response.UserResDto;
import com.myproject.task.userrestapi.entity.User;
import org.mapstruct.*;

@Mapper
public interface UserMapper {

    UserResDto mapToUserResDto(User user);

    @Mapping(target = "id", ignore = true)
    User mapToUser(UserCreateReqDto userCreateReqDto);

    @Mapping(target = "id", ignore = true)
    User mapToUser(UserUpdateReqDto userUpdateReqDto);

    @Mapping(target = "id", ignore = true)
    User mapToUser(UserFieldsUpdateReqDto userFieldsUpdateReqDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "updatedUser")
    User updateFields(User updatedUser, @MappingTarget User userToUpdate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "updatedUser")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateFieldsIfPresent(User updatedUser, @MappingTarget User userToUpdate);

}
