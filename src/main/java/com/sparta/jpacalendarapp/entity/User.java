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
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ScheduleAssignment> scheduleAssignments = new ArrayList<>();

    public User(PostUserRequestDto request, String password) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = password;
    }

    public void update(UpdateUserRequestDto request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }
}
