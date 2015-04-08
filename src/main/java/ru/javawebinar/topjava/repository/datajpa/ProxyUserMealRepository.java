package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by eugene on 01.04.15.
 */
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId")
    int deleteById(@Param("id") int id,@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal um WHERE um.user.id=:userId")
    int deleteAll(@Param("userId") int userId);


    @Query("SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId")
    UserMeal findOne(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:userId ORDER BY um.date DESC")
    List<UserMeal> findAll(@Param("userId") int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:userId and um.date >= :startDate and um.date <= :endDate ORDER BY um.date DESC")
    List<UserMeal> filterByDate(@Param("userId") int userId,
                                @Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate);


}
