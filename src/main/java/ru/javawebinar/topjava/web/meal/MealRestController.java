package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    Meal add(Meal meal) {
        return service.add(meal, AuthorizedUser.id());
    }

    Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    void update(Meal meal) {
        service.update(meal, AuthorizedUser.id());
    }

    void delete(Meal meal) {
        service.delete(meal, AuthorizedUser.id());
    }

    List<Meal> getAllByUserId() {
        return service.getAllByUserId(AuthorizedUser.id());
    }

    List<Meal> getAll() {
        return service.getAll();
    }
}