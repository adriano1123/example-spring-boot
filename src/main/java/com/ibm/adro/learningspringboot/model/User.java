package com.ibm.adro.learningspringboot.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    
    @JsonProperty("userUid")
    private final UUID userUid;

    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("lastName")
    private final String lastName;

    @JsonProperty("gender")
    private final Gender gender;

    @JsonProperty("age")
    private final Integer age;

    @JsonProperty("email")
    private final String email;

    public enum Gender {
        MALE, FEMALE
    }
}
