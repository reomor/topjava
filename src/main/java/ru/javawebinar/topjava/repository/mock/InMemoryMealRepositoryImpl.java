package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    private Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        } else {
            Meal mealById = get(meal.getId(), userId);
            if (mealById == null) {
                return null;
            }
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    private Meal get(int id) {
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

    private Meal delete(int id) {
        return repository.remove(id);
    }

    @Override
    public boolean delete(Meal meal, int userId) {
        Meal mealById = get(meal.getId(), userId);
        if (mealById != null) {
            return (delete(meal.getId()) != null);
        }
        return false;
    }

    @Override
    public List<Meal> getAllByUserId(int userId) {
        List<Meal> userMealList = new ArrayList<>();
        for (Meal meal : getAll()) {
            if (meal.getUserId() == userId) {
                userMealList.add(meal);
            }
        }
        return userMealList;
    }

    @Override
    public List<Meal> getAll() {
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
}

