package com.app.elect.auth.dto;

import com.app.elect.auth.database.entity.Gender;
import com.app.elect.auth.database.entity.Status;

import lombok.Value;

@Value
public class ClientFilerDto {
    Long userId;
    String address;
    Gender gender;
    Long providerScore;
    Status status;
    Long startAge;
    Long endAge;
}
