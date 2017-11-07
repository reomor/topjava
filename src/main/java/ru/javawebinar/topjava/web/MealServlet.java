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
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealDao mealDao;

    //private DateTimeFormatter jspDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doGet");
        request.setCharacterEncoding("UTF-8");

        String forwardPath = "meals.jsp";
        String action = request.getParameter("action");
        String mealId = request.getParameter("mealId");

        if (action != null){
            switch (action) {
                case "edit":
                    Meal meal = mealDao.getById(Integer.parseInt(mealId));
                    request.setAttribute("meal", meal);
                    break;
                case "delete":
                    mealDao.delete(Integer.parseInt(mealId));
                    response.sendRedirect("meals");
                    return;
                case "addnew":
                    request.removeAttribute("meal");
                    break;
                default:
                    log.debug("action: (" + action + ") is not supported.");
            }
        }

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

    private Meal createMealFromRequest(HttpServletRequest request) {
        String requestDescription = request.getParameter("description");
        LocalDateTime requestDateTime = LocalDateTime.parse(request.getParameter("datetime"));
        int requestCalories = Integer.parseInt(request.getParameter("calories"));

        return new Meal(requestDateTime, requestDescription, requestCalories);
    }

    // update or create new
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doPost");
        request.setCharacterEncoding("UTF-8");

        String requestId = request.getParameter("id");
        Meal meal = createMealFromRequest(request);

        if (requestId == null || requestId.isEmpty()) {
            // create new
            mealDao.add(meal);
        } else {
            // update existing
            meal.setId(Integer.parseInt(requestId));
            mealDao.update(meal);
        }

        log.debug("redirect from doPost to meals.jsp");
        response.sendRedirect("meals");
    }
}
