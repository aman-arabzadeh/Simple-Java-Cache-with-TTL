package com.company;

/**
 * Simple immutable user data object for caching examples.
 */
public record UserDTO(
        long userID,
        String firstName,
        String lastName,
        int age,
        String email
) {}
