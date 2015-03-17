package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.util.TimeUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMeal extends BaseEntity {

    protected String meal;

    protected Short calories;

    protected Date date;

    protected Integer userId;

    public UserMeal() {

    }

    public UserMeal(UserMeal meal) {
        this(meal.getId(), meal.getMeal(), meal.getCalories(), meal.getDate(), meal.getUserId());
    }

    public UserMeal(Integer id, String meal, Short calories, Date date, Integer userId) {
        this.id = id;
        this.meal = meal;
        this.calories = calories;
        this.date = date;
        this.userId = userId;
    }

    public UserMeal(String meal, Short calories, Date date, Integer userId) {
        this.meal = meal;
        this.calories = calories;
        this.date = date;
        this.userId = userId;
    }

    public UserMeal(String meal, Short calories, Date date) {
        this.meal = meal;
        this.calories = calories;
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", meal=" + meal +
                ", calories=" + calories +
                ", date=" + TimeUtil.toString(date) +
                ", userId=" + userId +
                "}";
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Short getCalories() {
        return calories;
    }

    public void setCalories(Short calories) {
        this.calories = calories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
