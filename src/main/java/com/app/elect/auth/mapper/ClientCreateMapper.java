package com.app.elect.auth.mapper;

import org.springframework.stereotype.Component;

import com.app.elect.auth.database.entity.Client;
import com.app.elect.auth.database.entity.User;
import com.app.elect.auth.database.repository.UserRepository;
import com.app.elect.auth.dto.ClientCreateDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientCreateMapper implements Mapper<ClientCreateDto, Client> {

    private final UserRepository userRepository;
    private final CommentCreateMapper commentCreateMapper;

    @Override
    public Client map(ClientCreateDto object) {
        User user = userRepository.findById(object.getUserId())
            .orElseThrow();
        Client newClient = new Client();
        newClient.setUser(user);
        newClient.setAddress(object.getAddress());
        newClient.setAge(object.getAge());
        newClient.setStatus(object.getStatus());
        newClient.setServices(object.getServices());
        newClient.setProviderScore(object.getProviderScore());
        newClient.setInterest(object.getInterest());
        newClient.setComfTimeStart(object.getComfTimeStart());
        newClient.setComfTimeEnd(object.getComfTimeEnd());
        newClient.setNumber(object.getNumber());
        newClient.setPrice(object.getPrice());
        newClient.getComments().add(commentCreateMapper.map(object.getComment()));
        return newClient;
    }

}
