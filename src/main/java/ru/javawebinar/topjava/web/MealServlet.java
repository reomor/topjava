package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("test", new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata")));
        int caloriesPerDay = 2000;
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(MealsUtil.getMockMealList(),
                LocalTime.MIN,
                LocalTime.MAX,
                caloriesPerDay);

        request.setAttribute("meals", filteredWithExceeded);
        //response.sendRedirect("meals.jsp");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
