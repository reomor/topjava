package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        return service.add(meal, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllFiltered(String dateFrom, String dateTo, String timeFrom, String timeTo) {

        LocalDate localDateFrom = (dateFrom == null || dateFrom.isEmpty()) ? LocalDate.MIN : LocalDate.parse(dateFrom);
        LocalDate localDateTo = (dateTo == null || dateTo.isEmpty()) ? LocalDate.MAX : LocalDate.parse(dateTo);
        LocalTime localTimeFrom = (timeFrom == null || timeFrom.isEmpty()) ? LocalTime.MIN : LocalTime.parse(timeFrom);
        LocalTime localTimeTo = (timeTo == null || timeTo.isEmpty()) ? LocalTime.MAX : LocalTime.parse(timeTo);

        // concat to one object
        //LocalDateTime LocalDateTime.of(LocalDate date, LocalTime time)

        return service.getAllFiltered(AuthorizedUser.id(),
                LocalDateTime.of(localDateFrom, localTimeFrom),
                LocalDateTime.of(localDateTo, localTimeTo));
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id());
    }
}