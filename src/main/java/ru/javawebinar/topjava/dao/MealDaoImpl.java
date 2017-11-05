package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;

public class MealDaoImpl implements MealDao {
    private static final Logger log = LoggerFactory.getLogger(MealDaoImpl.class);

    private final List<Meal> mockMealList = new ArrayList<>(MealsUtil.getMockMealList());

    @Override
    public void add(Meal meal) {
        meal.generateId();
        log.debug("meal added: " + meal);
        mockMealList.add(meal);
    }

    @Override
    public Meal getById(int id) {
        for (Meal meal: mockMealList) {
            if (meal.getId() == id) {
                log.debug("meal by id (" + id + ") found:" + meal );
                return meal;
            }
        }
        log.debug("meal by id (" + id + ") not found");
        return null;
    }

    @Override
    public void delete(int id) {
       Meal mealToDelete = getById(id);
       if (mealToDelete == null) {
           log.debug("meal by id (" + id + ") not found. Nothing to delete.");
           return;
       }
       log.debug("meal by id (" + id + ") deleted: " + mealToDelete);
       mockMealList.remove(mealToDelete);
    }

    @Override
    public void update(Meal meal) {
        Meal mealToUpdate = getById(meal.getId());
        if (mealToUpdate == null) {
            log.debug("meal by id (" + meal.getId() + ") not found. Nothing to update.");
            return;
        }
        int index = mockMealList.indexOf(mealToUpdate);
        mockMealList.set(index, meal);
        log.debug("meal: " + mealToUpdate + " at (" + index + ") with id (" + meal.getId() + ") updated to: " + meal);
    }

    @Override
    public List<Meal> getAll() {
        return mockMealList;
    }
}
