package com.ibm.adro.learningspringboot.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID userUid;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
    private String email;

    public enum Gender {
        MALE, FEMALE
    }
}
