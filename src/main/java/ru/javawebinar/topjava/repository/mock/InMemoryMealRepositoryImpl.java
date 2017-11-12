package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = get(id);
        if (meal.getUserId() != userId) {
            return null;
        }
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public void delete(int id, int userId) {
        Meal meal = get(id, userId);
        if (meal != null) {
            delete(id);
        }
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> values = new ArrayList<>(repository.values());
        Collections.sort(values, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                // по убыванию даты
                return -o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return values;
    }

    @Override
    public Collection<Meal> getAllbyUserId(int userId) {
        List<Meal> userMealList = new ArrayList<>();
        for (Meal meal : getAll()) {
            if (meal.getUserId() == userId) {
                userMealList.add(meal);
            }
        }
        return userMealList;
    }
}

