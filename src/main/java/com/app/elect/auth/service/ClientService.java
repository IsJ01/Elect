package com.app.elect.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.elect.auth.database.entity.Client;
import com.app.elect.auth.database.entity.Comment;
import com.app.elect.auth.database.entity.User;
import com.app.elect.auth.database.repository.ClientRepository;
import com.app.elect.auth.database.repository.UserRepository;
import com.app.elect.auth.dto.ClientCreateDto;
import com.app.elect.auth.dto.ClientReadDto;
import com.app.elect.auth.mapper.ClientCreateMapper;
import com.app.elect.auth.mapper.ClientReadMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateMapper clientCreateMapper;
    private final UserRepository userRepository;

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
            .map(clientReadMapper::map)
            .toList();
    }

    public PagedModel<ClientReadDto> findAllByUser(Long id, Pageable pageable) {
        ArrayList<Specification<Client>> spec = new ArrayList<>();

        spec.add((root, query, cb) -> {
            User user = userRepository.findById(id).get();
            return cb.equal(root.get("user"), user);
        });

        return new PagedModel<>(clientRepository.findAll(Specification.allOf(spec), pageable).map(clientReadMapper::map));
    }

    public Optional<ClientReadDto> findById(Long id) {
        return clientRepository.findById(id)
            .map(clientReadMapper::map);
    }

    @Transactional 
    public ClientReadDto create(ClientCreateDto createDto) {
        return Optional.of(createDto)
            .map(clientCreateMapper::map)
            .map(client -> {
                for (Comment comment: client.getComments()) {
                    comment.setClient(client);
                }
                return client;
            })
            .map(clientRepository::save)
            .map(clientReadMapper::map)
            .get();
    }

    @Transactional
    public boolean delete(Long id) {
        return clientRepository.findById(id)
            .map(entity -> {
                clientRepository.delete(entity);
                return true;
            })
            .orElse(false);
    }

}
