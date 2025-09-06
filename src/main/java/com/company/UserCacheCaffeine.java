package com.company;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Cache implementation using Caffeine.
 * TTL (configured globally).
 */
public class UserCacheCaffeine implements UserCache {

    private final Cache<Long, UserDTO> cache;

    public UserCacheCaffeine() {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .maximumSize(100)
                .build();
    }
    @Override
    public Optional<UserDTO> getUser(Long userId) {
        return Optional.ofNullable(cache.getIfPresent(userId));
    }
    @Override
    public void update(Long userId, UserDTO user) {
        cache.put(userId, user);
    }
    @Override
    public void remove(Long userId) {
        cache.invalidate(userId);
    }
    @Override
    public boolean userExists(Long userId) {
        return cache.getIfPresent(userId) != null;
    }
}
