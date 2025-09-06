package com.company;

/**
 * Demo main for testing CacheUser (ConcurrentHashMap) vs UserCacheCaffeine (Caffeine).
 */
public class Main {
    public static void main(String[] args) {
        var u1 = new UserDTO(23L, "Koray", "Aslan", 1000, "koray.aslan@telja.se");
        var u2 = new UserDTO(29L, "Ordinary", "Man", 300, "ordinaryman@gmail.com");

        // --- Test custom ConcurrentHashMap-based cache ---
        System.out.println("=== Testing CacheUser (ConcurrentHashMap) ===");
        UserCache concurrentHashMap = new CacheUser(1000);
        concurrentHashMap.update(u1.userID(), u1);
        concurrentHashMap.update(u2.userID(), u2);
        printUntilExpired(concurrentHashMap, u1, u2);
        System.out.println("User1 Exists?:  " + concurrentHashMap.userExists(u1.userID()));
        System.out.println("User2 Exists?:  " + concurrentHashMap.userExists(u2.userID()));
        System.out.println("Both entries expired in CacheUser.\n");

        // --- Test Caffeine-based cache ---
        System.out.println("=== Testing UserCacheCaffeine (Caffeine) ===");
        UserCache caffeineCache = new UserCacheCaffeine();
        caffeineCache.update(u1.userID(), u1);
        caffeineCache.update(u2.userID(), u2);
        printUntilExpired(caffeineCache, u1, u2);
        System.out.println("User1 Exists?:  " + caffeineCache.userExists(u1.userID()));
        System.out.println("User2 Exists?:  " + caffeineCache.userExists(u2.userID()));
        System.out.println("Both entries expired in UserCacheCaffeine.");
    }

    /**
     * Print cache values until both users are expired.
     */
    private static void printUntilExpired(UserCache cache, UserDTO u1, UserDTO u2) {
        while (cache.userExists(u1.userID()) || cache.userExists(u2.userID())) {
            System.out.println("User1: " + cache.getUser(u1.userID()));
            System.out.println("User2: " + cache.getUser(u2.userID()));
            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
