package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal add(Meal meal, int userId) throws NotFoundException {
        return checkNotFound(repository.save(meal, userId), "add meal=" + meal + " userId=" + userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFound(repository.get(id, userId), "get id=" + id + " userId=" + userId);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFound(repository.save(meal, userId), "update meal=" + meal + " userId=" + userId);
    }

    @Override
    public void delete(Meal meal, int userId) throws NotFoundException {
        checkNotFound(repository.delete(meal, userId), "delete meal=" + meal + " userId=" + userId);
    }

    @Override
    public List<Meal> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getAll() {
        return repository.getAll();
    }
}