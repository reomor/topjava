package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealDao mealDao = new MealDaoImpl();

    private DateTimeFormatter jspDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doGet");
        request.setCharacterEncoding("UTF-8");

        String forwardPath = "meals.jsp";
        String action = request.getParameter("action");
        String mealId = request.getParameter("mealId");

        if ("edit".equals(action)) {
            Meal meal = mealDao.getById(Integer.parseInt(mealId));
            request.setAttribute("meal", meal);
        } else if ("delete".equals(action)) {
            mealDao.delete(Integer.parseInt(mealId));
        } else if ("addnew".equals(action)) {
            request.removeAttribute("meal");
        }

        log.debug("put Meals in attributes");
        request.setAttribute("meals", mealDao.getAll());

        log.debug("put MealListWithExceeded to attributes");
        int caloriesPerDay = 2000;
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealDao.getAll(),
                LocalTime.MIN,
                LocalTime.MAX,
                caloriesPerDay);

        request.setAttribute("mealsWithExceed", filteredWithExceeded);

        //response.sendRedirect("meals.jsp");
        log.debug("forward from doGet to meals.jsp");
        request.getRequestDispatcher(forwardPath).forward(request, response);
    }

    // update or create new
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doPost");
        request.setCharacterEncoding("UTF-8");

        String requestId = request.getParameter("id");
        String requestDescription = request.getParameter("description");
        LocalDateTime requestDateTime = LocalDateTime.parse(request.getParameter("datetime"));
        int requestCalories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(requestDateTime, requestDescription, requestCalories);

        if (requestId == null || requestId.isEmpty()) {
            // create new
            mealDao.add(meal);
        } else {
            // update existing
            meal.setId(Integer.parseInt(requestId));
            mealDao.update(meal);
        }

        log.debug("redirect from doPost to meals.jsp");
        //request.getRequestDispatcher("meals.jsp").forward(request, response);
        //response.sendRedirect("meals.jsp");
        //response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        //response.setHeader("Location", "meals.jsp");
        //this.doGet(request, response);
        response.sendRedirect("meals");
    }
}
