package com.micro.userservice.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "micro_users")
public class Users {
    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "E-mail")
    private String email;

    @Column(name = "about")
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();
}
