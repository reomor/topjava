package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

//order is importaint
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
//@ActiveProfiles("jdbc")
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL01_ID, USER_ID);
        assertMatch(meal, MEAL01);
    }

    @Test(expected = NotFoundException.class)
    public void getWithForeignUserID() throws Exception {
        Meal meal = service.get(MEAL01_ID, ADMIN_ID);
        assertMatch(meal, MEAL01);
    }

    @Test
    public void getAll() throws Exception {
        final List<Meal> actual = service.getAll(USER_ID);
        assertMatch(actual, MEAL01, MEAL02, MEAL03, MEAL04, MEAL05, MEAL06);
    }

    @Test
    public void getBetweenDates() throws Exception {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final List<Meal> actual = service.getBetweenDates(LocalDate.parse("2015-05-30", formatter),
                LocalDate.parse("2015-05-30", formatter),
                USER_ID);
        assertMatch(actual, MEAL01, MEAL02, MEAL03);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final List<Meal> actual = service.getBetweenDateTimes(LocalDateTime.parse("2015-05-30 13:00:00", formatter),
                LocalDateTime.parse("2015-05-31 10:00:00", formatter),
                USER_ID);
        assertMatch(actual, MEAL04, MEAL02, MEAL03);
    }

    @Test
    public void create() throws Exception {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Meal meal = new Meal(null, LocalDateTime.parse("2015-06-01 10:00:00", formatter), "Завтрак", 666);
        Meal mealCreated = service.create(meal, USER_ID);
        meal.setId(mealCreated.getId());
        assertMatch(mealCreated, meal);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL02);
        updated.setDescription("ОбедОбед");
        updated.setCalories(555);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL02_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithForeignId() throws Exception {
        Meal updated = new Meal(MEAL02);
        updated.setDescription("ОбедОбед");
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL02_ID, USER_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL01_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL02, MEAL03, MEAL04, MEAL05, MEAL06);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithForeignId() throws Exception {
        service.delete(MEAL01_ID, ADMIN_ID);
        assertMatch(service.getAll(USER_ID), MEAL02, MEAL03, MEAL04, MEAL05, MEAL06);
    }

    /*
    @Test
    public void checkEqualTo() {
        Meal meal01_edited = new Meal(MEAL01);
        meal01_edited.setDescription("Dinner");
        assertMatch(meal01_edited, MEAL01);
    }
    //*/
}