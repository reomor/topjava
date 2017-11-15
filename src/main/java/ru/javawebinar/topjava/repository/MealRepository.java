package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int userId);

    //void delete(int id);

    boolean delete(int id, int userId);

    //Meal get(int id);

    Meal get(int id, int userId);

    //List<MealWithExceed> getAllFiltered(int userId, LocalDateTime localDTFrom, LocalDateTime localDTTo);

    List<Meal> getAll();

    List<Meal> getAllByUserId(Integer userId);
}
