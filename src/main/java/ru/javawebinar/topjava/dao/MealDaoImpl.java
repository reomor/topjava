package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.storage.Storage;

import java.util.List;

public class MealDaoImpl implements MealDao {
    private static final Logger log = LoggerFactory.getLogger(MealDaoImpl.class);

    private final Storage storage;

    public MealDaoImpl() {
        storage = new MemoryStorage();
        storage.initStorage();
    }

    @Override
    public void add(Meal meal) {
        storage.add(meal);
    }

    @Override
    public Meal getById(int id) {
        return storage.getById(id);
    }

    @Override
    public void delete(int id) {
       storage.delete(id);
    }

    @Override
    public void update(Meal meal) {
        storage.update(meal);
    }

    @Override
    public List<Meal> getAll() {
        return storage.getAll();
    }
}
