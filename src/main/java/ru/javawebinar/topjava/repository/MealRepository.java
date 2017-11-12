package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int userId);

    //void delete(int id);

    boolean delete(Meal meal, int userId);

    //Meal get(int id);

    Meal get(int id, int userId);

    List<Meal> getAll();

    List<Meal> getAllByUserId(int userId);
}
