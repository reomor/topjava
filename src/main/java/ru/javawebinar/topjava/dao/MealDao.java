package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void add(Meal meal);
    Meal getById(int id);
    void delete(int id);
    void update(Meal meal);
    List<Meal> getAll();
}
