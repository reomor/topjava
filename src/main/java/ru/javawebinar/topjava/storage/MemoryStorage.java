package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryStorage implements Storage {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(MemoryStorage.class);

    private static List<Meal> meals = new ArrayList<>();

    public void initStorage() {
        log.debug("Storage initialized with some test data");
        List<Meal> mealsWithoutId = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),

                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),

                new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "Завтрак", 1010),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 20, 0), "Ужин", 510)
        );

        for (int i = 0; i < mealsWithoutId.size(); i++) {
            Meal meal = mealsWithoutId.get(i);
            meal.setId(getGeneratedId());
            meals.add(meal);
        }
    }

    private int getGeneratedId() {
        return idGenerator.incrementAndGet();
    }

    @Override
    public synchronized void add(Meal meal) {
        int id = getGeneratedId();
        log.debug("id (" + id + ") generated");
        meal.setId(id);
        meals.add(meal);
    }

    @Override
    public synchronized Meal getById(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public synchronized void delete(int id) {
        Meal mealToDelete = getById(id);
        if (mealToDelete == null) {
            log.debug("meal by id (" + id + ") not found. Nothing to delete.");
            return;
        }
        log.debug("meal by id (" + id + ") deleted: " + mealToDelete);
        meals.remove(mealToDelete);
    }

    @Override
    public synchronized void update(Meal meal) {
        Meal mealToUpdate = getById(meal.getId());
        if (mealToUpdate == null) {
            log.debug("meal by id (" + meal.getId() + ") not found. Nothing to update.");
            return;
        }

        int index = meals.indexOf(mealToUpdate);
        meals.set(index, meal);
        log.debug("meal: " + mealToUpdate + " at (" + index + ") with id (" + meal.getId() + ") updated to: " + meal);

    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }
}
