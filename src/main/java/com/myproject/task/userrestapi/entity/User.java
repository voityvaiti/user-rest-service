package com.myproject.task.userrestapi.entity;

import com.myproject.task.userrestapi.validation.annotation.user.BirthDateConstraints;
import com.myproject.task.userrestapi.validation.annotation.user.EmailConstraints;
import com.myproject.task.userrestapi.validation.annotation.user.FirstNameConstraints;
import com.myproject.task.userrestapi.validation.annotation.user.LastNameConstraints;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @EmailConstraints
    private String email;

    @Column(name = "first_name")
    @FirstNameConstraints
    private String firstName;

    @Column(name = "last_name")
    @LastNameConstraints
    private String lastName;

    @Column(name = "birth_date")
    @BirthDateConstraints
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "tel_number")
    private String telNumber;

}
