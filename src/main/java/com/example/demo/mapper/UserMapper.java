package com.example.demo.mapper;


import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper Instance = Mappers.getMapper(UserMapper.class);


    User userDtoToUser(UserDto userDto);

    UserDto usersToUsersDto(User user);

 


}
