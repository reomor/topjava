package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
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
        return checkNotFound(repository.save(meal, userId), "operation add (meal=" + meal + " userId=" + userId + ")");
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFound(repository.get(id, userId), "operation get (id=" + id + " userId=" + userId + ")");
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFound(repository.save(meal, userId), "operation update meal=" + meal + " userId=" + userId + ")");
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFound(repository.delete(id, userId), "operation delete id=" + id + " userId=" + userId + ")");
    }

    @Override
    public List<MealWithExceed> getAllFiltered(int userId, LocalDateTime localDTFrom, LocalDateTime localDTTo) {
        Collection<Meal> meals = repository.getAllByUserId(userId);
        return MealsUtil.getFilteredWithExceededDT(meals, localDTFrom, localDTTo, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        return getAllFiltered(userId, LocalDateTime.MIN, LocalDateTime.MAX);
    }
}