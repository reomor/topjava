package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.lang.reflect.Array;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }
    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "new meal", 666);

        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(resultActions, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(USER_ID), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testUpdated() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("updated meal");
        updated.setCalories(999);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void testBetweenDT() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(REST_URL + "betweendt?startDateTime=2015-05-30T10:00:00&endDateTime=2015-05-30T13:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                // got MealWithExceed
                //.andExpect(contentJson(MEAL4));
        List<MealWithExceed> actual = TestUtil.readValuesFromJson(resultActions, MealWithExceed.class);
        List<MealWithExceed> expected = MealsUtil.getWithExceeded(Arrays.asList(MEAL2, MEAL1), AuthorizedUser.getCaloriesPerDay());

        assertMatch(actual, expected, true);
    }

    @Test
    public void testBetween() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(REST_URL + "between?startDate=2015-05-30&endDate=2015-05-30&startTime=10:00:00&endTime=13:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // got MealWithExceed
        //.andExpect(contentJson(MEAL4));
        List<MealWithExceed> actual = TestUtil.readValuesFromJson(resultActions, MealWithExceed.class);
        List<MealWithExceed> expected = MealsUtil.getWithExceeded(Arrays.asList(MEAL2, MEAL1), AuthorizedUser.getCaloriesPerDay());

        assertMatch(actual, expected, true);
    }
}