# Java Caching Examples

Simple demo comparing a manual cache with `ConcurrentHashMap` and a modern cache using [Caffeine](https://github.com/ben-manes/caffeine).

# Read More 
[Introduction to Caffeine](https://www.baeldung.com/java-caching-caffeine#3-asynchronous-loading).

```java
// Manual cache with ConcurrentHashMap
private final ConcurrentHashMap<Long, DataObject> users = new ConcurrentHashMap<>();



// Caffeine cache
Cache<Long, DataObject> cache = Caffeine.newBuilder()
    .expireAfterWrite(1, TimeUnit.SECONDS)
    .maximumSize(100)
    .build();

// Caffeine Synchronous Loading
LoadingCache<Long, DataObject> loadingCache = Caffeine.newBuilder()
    .expireAfterWrite(1, TimeUnit.SECONDS)
    .maximumSize(100)
    .build(k -> DataObject.get("Data for " + k));

// asynchronous cache loading
AsyncLoadingCache<String, DataObject> asyncCache = Caffeine.newBuilder()
    .expireAfterWrite(1, TimeUnit.SECONDS)
     .maximumSize(100)
    .buildAsync(k -> DataObject.get("Data for " + k));
