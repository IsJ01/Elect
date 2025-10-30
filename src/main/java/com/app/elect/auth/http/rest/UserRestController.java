package com.app.elect.auth.http.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.elect.auth.database.entity.User;
import com.app.elect.auth.dto.UserReadDto;
import com.app.elect.auth.mapper.UserReadMapper;
import com.app.elect.auth.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserRestController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserReadMapper userReadMapper;

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable Long id) {    
        return userDetailsServiceImpl.findById(id);
    }

    @GetMapping("/current")
    public ResponseEntity<?> current(@AuthenticationPrincipal UserDetails user) {    
        return ResponseEntity.ok().body(userReadMapper.map((User) user));
    }

}
