package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DatajpaUserServiceTest extends UserServiceTest {
    @Test
    public void testGetUserWithMeals() throws Exception {
        User actual = service.getUserWithMeals(USER_ID);
        assertMatch(actual.getMeals(), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }
}