package com.app.elect.auth.database.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

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
    private LocalTime comfTimeStart;

    @Column(nullable = false)
    private LocalTime comfTimeEnd;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String price;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void setComments(List<Comment> comments) {
        this.comments = new ArrayList<>(comments);
    }

}
