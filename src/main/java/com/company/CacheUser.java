package com.company;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple custom cache implementation using ConcurrentHashMap.
 *
 */
public class CacheUser implements UserCache {

    private final ConcurrentHashMap<Long, UserEntryCache> users = new ConcurrentHashMap<>();
    private final long defaultTtlMillis;

    public CacheUser(long defaultTtlMillis) {
        this.defaultTtlMillis = defaultTtlMillis;
    }

    @Override
    public Optional<UserDTO> getUser(Long userID) {
        var entry = users.get(userID);
        if (entry == null || entry.hasExpired()) {
            remove(userID);
            return Optional.empty();
        }
        return Optional.of(entry.user());
    }

    @Override
    public void update(Long userId, UserDTO user) {
        update(userId, user, defaultTtlMillis);
    }

    public void update(long userId, UserDTO userDTO, long ttlMillis) {
        long expiryTime = System.currentTimeMillis() + ttlMillis;
        users.put(userId, new UserEntryCache(userDTO, expiryTime));
    }
    @Override
    public void remove(Long userId) {
        users.remove(userId);
    }

    @Override
    public boolean userExists(Long userId) {
        {
            return getUser(userId).isPresent();
        }
    }

    private record UserEntryCache(UserDTO user, long expiryTime) {
        boolean hasExpired() {
            return System.currentTimeMillis() > this.expiryTime;
        }
    }
}
