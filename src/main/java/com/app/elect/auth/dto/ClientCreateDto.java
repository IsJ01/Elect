package com.app.elect.auth.dto;

import java.time.LocalTime;

import com.app.elect.auth.database.entity.Gender;
import com.app.elect.auth.database.entity.Status;

import lombok.Value;

@Value
public class ClientCreateDto {
    Long userId;
    String address;
    Gender gender;
    Long age;
    Status status;
    String services;
    Long providerScore;
    String interest;
    LocalTime comfTimeStart;
    LocalTime comfTimeEnd;
    String number;
    String price;
    CommentCreateDto comment;
}
