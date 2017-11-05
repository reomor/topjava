package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Meal {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    // генерация ид непосредственно перед записью в список, иначе будут висящие meal в сервлете c занятым ид и пустыми полями
    public void generateId() {
        this.id = idGenerator.incrementAndGet();
    }

    // отдельный констуктор для создания тестовых начальных данных
    public Meal(LocalDateTime dateTime, String description, int calories, boolean generateId) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id = idGenerator.incrementAndGet();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
