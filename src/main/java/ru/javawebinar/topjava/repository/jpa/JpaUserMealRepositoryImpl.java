package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 20.03.2015.
 */
@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository{
    LoggerWrapper LOG = LoggerWrapper.get(JpaUserMealRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User ref = em.getReference(User.class, userId);
        userMeal.setUser(ref);
        if (userMeal.isNew()) {
            em.persist(userMeal);
        } else {
            if (get(userMeal.getId(), userId) == null)
                return null;
            em.merge(userMeal);
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter(1, id)
                .setParameter(2, userId)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public boolean deleteAll(int userId) {
        return em.createNamedQuery(UserMeal.DELETE_ALL)
                .setParameter(1, userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMeals = em.createNamedQuery(UserMeal.GET_MEAL, UserMeal.class)
                .setParameter(1, id)
                .setParameter(2, userId)
                .getResultList();
        return (userMeals == null || userMeals.size() != 1) ? null : userMeals.get(0);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class)
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> filterByDate(LocalDateTime start, LocalDateTime end, int userId) {
        return em.createNamedQuery(UserMeal.FILTER_BY_DATE, UserMeal.class)
                .setParameter(1, userId)
                .setParameter(2, start)
                .setParameter(3, end)
                .getResultList();
    }
}
