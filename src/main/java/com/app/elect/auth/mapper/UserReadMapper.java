package com.app.elect.auth.mapper;

import org.springframework.stereotype.Component;

import com.app.elect.auth.database.entity.User;
import com.app.elect.auth.dto.UserReadDto;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
            .id(object.getId())
            .username(object.getUsername())
            .authorities(object.getAuthorities())
            .build();
    }

}
