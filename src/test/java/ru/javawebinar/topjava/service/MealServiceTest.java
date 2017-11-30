package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.*;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static final Logger log = getLogger(MealServiceTest.class);

    static {
        SLF4JBridgeHandler.install();
    }

    // doesn't work properly with TestTiming
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private static Map<String, Long> testsTime = new HashMap<>();

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        long startTime;
        @Override
        protected void starting(Description description) {
            startTime = System.nanoTime();
        }

        @Override
        protected void finished(Description description) {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;
            testsTime.put(description.getMethodName(), duration);
//            System.out.println(description.getMethodName() + " lasts " + duration + "ms");
            log.info(description.getMethodName() + " lasts " + duration + "ms");
        }
    };

    @AfterClass
    public static void printTestsTime() {
        log.info("=== Tests duration ===");
        for(Map.Entry<String, Long> entry : testsTime.entrySet()) {
            //System.out.println(entry.getKey() + ": " + entry.getValue() + "ms");
            log.info(entry.getKey() + ": " + entry.getValue() + "ms");
        }
        log.info("=== /Tests duration ===");
    }

    @Autowired
    private MealService service;

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            log.info(TimeUnit.NANOSECONDS.toMillis(nanos) + "ms");
        }
    };

    @Test
    @Transactional
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    @Transactional
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    @Transactional
    public void testSave() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    @Transactional
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    @Transactional
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    @Transactional
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    @Transactional
    public void testGetBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }

    @Test
    @Transactional
    public void testGetBetweenFullTime() throws Exception {
        assertMatch(service.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 13, 0), USER_ID), MEAL5, MEAL4);
    }
}