package com.app.elect.auth.dto;

import java.time.LocalTime;

import com.app.elect.auth.database.entity.Status;

public class ClientEditDto {
    Long age;
    Status status;
    String services;
    Long providerScore;
    String interest;
    LocalTime comfTimeStart;
    LocalTime comfTimeEnd;
    String number;
    String price;
    String comment;
}
