package com.app.elect.auth.mapper;

import org.springframework.stereotype.Component;

import com.app.elect.auth.database.entity.Client;
import com.app.elect.auth.dto.ClientReadDto;
import com.app.elect.auth.dto.UserReadDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientReadMapper implements Mapper<Client, ClientReadDto> {

    private final UserReadMapper userReadMapper;
    private final CommentReadMapper commentReadMapper;

    @Override
    public ClientReadDto map(Client object) {
        UserReadDto user = userReadMapper.map(object.getUser());
        ClientReadDto clientReadDto = new ClientReadDto(
            object.getId(), 
            user, 
            object.getAddress(), 
            object.getGender(), 
            object.getAge(), 
            object.getStatus(), 
            object.getServices(), 
            object.getProviderScore(), 
            object.getInterest(),
            object.getComfTimeStart(), 
            object.getComfTimeEnd(), 
            object.getNumber(), 
            object.getPrice(),
            object.getComments().stream()
                .map(commentReadMapper::map)
                .toList()
        );
        return clientReadDto;
    }

    

}
