package com.example.savingsappbackend.mappers;

import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.dto.SignUpDto;
import com.example.savingsappbackend.models.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
