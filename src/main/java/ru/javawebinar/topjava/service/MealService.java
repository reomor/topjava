package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    Meal add(Meal meal, int userId);

    Meal get(int id, int userId);

    void update(Meal meal, int userId);

    void delete(int id, int userId);

    List<MealWithExceed> getAllFiltered(int userId, LocalDateTime localDTFrom, LocalDateTime localDTTo);

    List<Meal> getAllByUserId(int userId);

    List<Meal> getAll();
}