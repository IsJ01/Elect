package com.app.elect.auth.database.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
@Entity
public class Client implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Gender gender = Gender.MALE;

    private Long age;

    private Status status;

    @Column(nullable = false)
    private String services;

    @Builder.Default
    @Column(nullable = false)
    private Long providerScore = 1L;

    @Column(nullable = false)
    private String interest;

    @Column(nullable = false)
    private LocalDateTime comfTimeStart;

    @Column(nullable = false)
    private LocalDateTime comfTimeEnd;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String price;

    private String comment;

}
