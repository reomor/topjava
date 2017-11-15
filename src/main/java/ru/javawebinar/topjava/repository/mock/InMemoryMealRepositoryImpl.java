package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        } else {
            Meal mealById = get(meal.getId(), userId);
            if (mealById == null) {
                return null;
            }
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }
/*
    private Meal get(int id) {
        return repository.get(id);
    }
*/

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != userId) {
            return null;
        }
        return meal;
    }
/*
    private Meal delete(int id) {
        return repository.remove(id);
    }
*/

    @Override
    public boolean delete(int id, int userId) {
        Meal mealById = get(id, userId);
        return mealById != null && (repository.remove(id) != null);
    }

    @Override
    public List<Meal> getAllByUserId(Integer userId) {
        //values.sort(Comparator.comparing(AbstractNamedEntity::getName).thenComparing(...))
        return repository.values().stream()
                .filter(meal -> userId == null || userId.equals(meal.getUserId()))
                .sorted((o1, o2) -> -o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll() {
        return getAllByUserId(null);
    }
}

