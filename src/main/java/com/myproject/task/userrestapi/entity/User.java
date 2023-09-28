package com.myproject.task.userrestapi.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject.task.userrestapi.validator.annotation.UserAgeConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @NotEmpty(message = "E-mail is required.")
    @Email(message = "E-mail have to be valid.")
    private String email;

    @Column(name = "first_name")
    @NotEmpty(message = "First name is required.")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name is required.")
    private String lastName;

    @Column(name = "birth_date")
    @NotNull(message = "Birth date is required.")
    @UserAgeConstraint
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "tel_number")
    private String telNumber;

    public User(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(birthDate, user.birthDate) && Objects.equals(address, user.address) && Objects.equals(telNumber, user.telNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, birthDate, address, telNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", telNumber='" + telNumber + '\'' +
                '}';
    }
}
