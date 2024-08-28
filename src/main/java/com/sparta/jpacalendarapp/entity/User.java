package com.sparta.jpacalendarapp.entity;

import com.sparta.jpacalendarapp.dto.user.request.PostUserRequestDto;
import com.sparta.jpacalendarapp.dto.user.request.UpdateUserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends Timestamped{
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ScheduleAssignment> scheduleAssignments = new ArrayList<>();

    public User(PostUserRequestDto request, String password, UserRoleEnum role) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = password;
        this.role = role;
    }

    public void update(UpdateUserRequestDto request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }
}
