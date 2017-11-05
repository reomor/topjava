package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealDao mealDao = new MealDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("test", new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata")));
        log.debug("put MealListWithExceeded to attributes and redirect to meals.jsp");
        int caloriesPerDay = 2000;
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(MealsUtil.getMockMealList(),
                LocalTime.MIN,
                LocalTime.MAX,
                caloriesPerDay);

        request.setAttribute("meals", filteredWithExceeded);
        //response.sendRedirect("meals.jsp");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
