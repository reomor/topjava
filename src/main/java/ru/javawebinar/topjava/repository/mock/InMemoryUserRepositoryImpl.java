package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    // false if not found +
    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        User result = repository.remove(id);
        return result != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }
    // null if not found +
    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> values = new ArrayList<>(repository.values());
        //(o1, o2) -> o1.getName().compareTo(o2.getName())
        //values.sort(Comparator.comparing(AbstractNamedEntity::getName));
        values.sort((o1, o2) -> {
            if (o1.getName().compareTo(o2.getName()) != 0) {
                return o1.getName().compareTo(o2.getName());
            }
            return o1.getEmail().compareTo(o2.getEmail());
        });
        return values;
    }
    // null if not found +
    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
/*

        for (User user : repository.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
*/
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

/*
    {
        repository.put(1, new User(1, "bbb", "email", "email", Role.ROLE_ADMIN, Role.ROLE_USER));
        repository.put(2, new User(2, "aaa", "email2", "email", Role.ROLE_ADMIN, Role.ROLE_USER));
    }

    public static void main(String[] args) {
        InMemoryUserRepositoryImpl inMemoryUserRepository = new InMemoryUserRepositoryImpl();
        System.out.println(inMemoryUserRepository.getAll());
    }
*/

}
