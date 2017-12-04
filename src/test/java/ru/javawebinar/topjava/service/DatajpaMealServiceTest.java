package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
public class DatajpaMealServiceTest extends MealServiceTest {
    @Test
    public void testGetMealWithUser() throws Exception {
        Meal actual = service.getMealWithUser(MEAL1_ID + 1, USER_ID);
        assertMatch(actual.getUser(), USER);
    }
}