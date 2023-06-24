package com.calenDARE.storageservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passwort", nullable = false)
    private String passwort;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> events;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
    }
}
