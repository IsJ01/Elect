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
import com.app.elect.auth.dto.ClientFilerDto;
import com.app.elect.auth.dto.ClientReadDto;
import com.app.elect.auth.dto.ScoreReadDto;
import com.app.elect.auth.mapper.ClientCreateMapper;
import com.app.elect.auth.mapper.ClientReadMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateMapper clientCreateMapper;
    private final UserRepository userRepository;

    private ClientFilerDto getFilter(Long v) {
        return new ClientFilerDto(null,null,null,v,null,null,null);
    }

    public ScoreReadDto getScore() {
        return new ScoreReadDto(
            Long.valueOf(findAllByFilter(getFilter(1L)).size()),
            Long.valueOf(findAllByFilter(getFilter(2L)).size()),
            Long.valueOf(findAllByFilter(getFilter(3L)).size()),
            Long.valueOf(findAllByFilter(getFilter(4L)).size()),
            Long.valueOf(findAllByFilter(getFilter(5L)).size())
        );
    }

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
            .map(clientReadMapper::map)
            .toList();
    }

    public List<ClientReadDto> findAllByFilter(ClientFilerDto filerDto) {
        ArrayList<Specification<Client>> spec = new ArrayList<>();
        
        if (filerDto.getUserId() != null) {
            User user = userRepository.findById(filerDto.getUserId()).get();
            spec.add((root, query, cb) -> cb.equal(root.get("user"), user));
        }

        if (filerDto.getAddress() != null) {
            spec.add((root, query, cb) -> cb.like(root.get("address"), "%" + filerDto.getAddress() + '%'));
        }

        if (filerDto.getProviderScore() != null) {
            spec.add((root, query, cb) -> cb.equal(root.get("providerScore"), filerDto.getProviderScore()));
        }

        if (filerDto.getGender() != null) {
            spec.add((root, query, cb) -> cb.equal(root.get("gender"), filerDto.getGender().name()));
        }

        if (filerDto.getStatus() != null) {
            spec.add((root, query, cb) -> cb.equal(root.get("status"), filerDto.getStatus().name()));
        }

        if (filerDto.getStartAge() != null) {
            spec.add((root, query, cb) -> cb.greaterThan(root.get("age"), filerDto.getStartAge()));
        }

        if (filerDto.getEndAge() != null) {
            spec.add((root, query, cb) -> cb.lessThan(root.get("age"), filerDto.getEndAge()));
        }

        return clientRepository.findAll(Specification.allOf(spec)).stream()
            .map(clientReadMapper::map)
            .toList();
    }

    public PagedModel<ClientReadDto> findAllByFilter(Pageable pageable, ClientFilerDto filerDto) {
        ArrayList<Specification<Client>> spec = new ArrayList<>();
        
        if (filerDto.getUserId() != null) {
            User user = userRepository.findById(filerDto.getUserId()).get();
            spec.add((root, query, cb) -> cb.equal(root.get("user"), user));
        }

        if (filerDto.getAddress() != null) {
            spec.add((root, query, cb) -> cb.like(root.get("address"), "%" + filerDto.getAddress() + '%'));
        }

        if (filerDto.getProviderScore() != null) {
            spec.add((root, query, cb) -> cb.equal(root.get("providerScore"), filerDto.getProviderScore()));
        }

        if (filerDto.getGender() != null) {
            spec.add((root, query, cb) -> cb.equal(root.get("gender"), filerDto.getGender().name()));
        }

        if (filerDto.getStatus() != null) {
            spec.add((root, query, cb) -> cb.equal(root.get("status"), filerDto.getStatus().name()));
        }

        if (filerDto.getStartAge() != null) {
            spec.add((root, query, cb) -> cb.greaterThan(root.get("age"), filerDto.getStartAge()));
        }

        if (filerDto.getEndAge() != null) {
            spec.add((root, query, cb) -> cb.lessThan(root.get("age"), filerDto.getEndAge()));
        }

        return new PagedModel<>(clientRepository.findAll(Specification.allOf(spec), pageable).map(clientReadMapper::map));
    } 

    public PagedModel<ClientReadDto> findAllByUser(Pageable pageable, Long id) {
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
