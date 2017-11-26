package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            //if (ref.equals(meal.getUser())) {
            if (meal.getUser().getId() == userId) {
                return em.merge(meal);
            }
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userid", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        final List<Meal> resultList = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("userid", userId)
                .getResultList();
        return resultList.size() != 1 ? null : resultList.get(0);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class)
                .setParameter("userid", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        // "SELECT m FROM Meal m WHERE m.user.id=:userid AND m.dateTime BETWEEN :dtFrom AND :dtTo ORDER BY m.dateTime DESC"
        return em.createNamedQuery(Meal.GET_BETWEEN_SORTED, Meal.class)
                .setParameter("userid", userId)
                .setParameter("dtFrom", startDate)
                .setParameter("dtTo", endDate)
                .getResultList();
    }
}