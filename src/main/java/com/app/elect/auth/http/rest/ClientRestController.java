package com.app.elect.auth.http.rest;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.elect.auth.dto.ClientCreateDto;
import com.app.elect.auth.dto.ClientReadDto;
import com.app.elect.auth.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok()
            .body(clientService.findAll());
    }

    @GetMapping("/by-user/{id}")
    public PagedModel<ClientReadDto> findAllByUser(@PathVariable Long id, 
        @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return clientService.findAllByUser(id, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok()
            .body(clientService.findById(id));
    }

    @Transactional 
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientCreateDto createDto) {
        return ResponseEntity.status(201)
            .body(clientService.create(createDto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Long id) {
        return clientService.delete(id) ?
            notFound().build()
            :
            ok().build();
    }

}
