package com.company;

import java.util.Optional;

/**
 * Simple interface for user cache implementations.
 */
public interface UserCache {
    Optional<UserDTO> getUser(Long userId);
    void update(Long userId, UserDTO user);
    void remove(Long userId);
    boolean userExists(Long userId);
}
