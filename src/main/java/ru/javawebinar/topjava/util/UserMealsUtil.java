package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }
    /*
    Map<String, Integer> items = new HashMap<>();
    items.put("A", 10);
    items.put("B", 20);
    items.put("C", 30);
    items.put("D", 40);
    items.put("E", 50);
    items.put("F", 60);

    items.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));

    items.forEach((k,v)->{
        System.out.println("Item : " + k + " Count : " + v);
        if("E".equals(k)){
            System.out.println("Hello E");
        }
    });
     */
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        mealList.forEach();
        return null;
    }
    /*
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // подсчитываем в мап день - количество каллорий за день
        Map<LocalDate, Integer> mapSumCaloriesPerDay = mealList
                .stream()
                // группировка по дате с подсчетом поля каллорий
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));
        // формирование списка объектов нового типа UserMealWithExceed в соответствии с условием выбора
        List<UserMealWithExceed> mealWithExceedsList = mealList
                .stream()
                // фильтрация записей согласно временному диапазону
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                // создание объектов нового типа с дополнительным полем
                .map(userMeal -> {
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(),
                            userMeal.getDescription(),
                            userMeal.getCalories(),
                            // получение калорий за день из ранее сформированного мапа
                            mapSumCaloriesPerDay.get(userMeal.getLocalDate()) > caloriesPerDay);
                    return userMealWithExceed;
                })
                .collect(Collectors.toList());
        return mealWithExceedsList;
    }
    //*/
}
