package com.company.jetassesment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name="employee")
@Table(name="employee")
public class Employee {
    @Schema(hidden = true) // Exclude UUID from the sample JSON request
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @NotBlank
    @Email
    @Column(name="email",unique = true, nullable=false)
    private String email;
    @NotBlank
    @Column(name="first_name", nullable=false)
    private String firstName;
    @NotBlank
    @Column(name="last_name", nullable=false)
    private String lastName;
    @NotNull
    @Column(name="dob", nullable=false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "hobby")
    private List<String> hobbies = new ArrayList<>();

    public Employee() {
    }

    public Employee(String email, String firstName, String lastName, LocalDate birthday, List<String> hobbies) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.hobbies = hobbies;
    }

    // getters and setters

    public UUID getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}

