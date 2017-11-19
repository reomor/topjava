package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final int MEAL01_ID = START_SEQ + 2;
    public static final int MEAL02_ID = START_SEQ + 3;
    public static final int MEAL03_ID = START_SEQ + 4;
    public static final int MEAL04_ID = START_SEQ + 5;
    public static final int MEAL05_ID = START_SEQ + 6;
    public static final int MEAL06_ID = START_SEQ + 7;

    public static final Meal MEAL01 = new Meal(MEAL01_ID, LocalDateTime.parse("2015-05-30 10:00:00", formatter), "Завтрак", 1000 );
    public static final Meal MEAL02 = new Meal(MEAL02_ID, LocalDateTime.parse("2015-05-30 13:00:00", formatter), "Обед", 500 );
    public static final Meal MEAL03 = new Meal(MEAL03_ID, LocalDateTime.parse("2015-05-30 20:00:00", formatter), "Ужин", 500 );

    public static final Meal MEAL04 = new Meal(MEAL04_ID, LocalDateTime.parse("2015-05-31 10:00:00", formatter), "Ужин", 1000 );
    public static final Meal MEAL05 = new Meal(MEAL05_ID, LocalDateTime.parse("2015-05-31 13:00:00", formatter), "Ужин", 555 );
    public static final Meal MEAL06 = new Meal(MEAL06_ID, LocalDateTime.parse("2015-05-31 20:00:00", formatter), "Ужин", 450 );

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, Meal ... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).hasSameElementsAs(expected);
    }

    public static void main(String[] args) {
        System.out.println(MEAL01);
        System.out.println(MEAL02);
        System.out.println(MEAL03);
    }
}
