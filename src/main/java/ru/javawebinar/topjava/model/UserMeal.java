package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.LoggedUser;

import java.util.Date;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMeal extends BaseEntity {

    private String meal;

    private Short calories;

    private Date date;

    private Integer userId;

    public UserMeal() {
        this.userId = LoggedUser.id();
    }

    public UserMeal(String meal, Short calories, Date date) {
        this.meal = meal;
        this.calories = calories;
        this.date = date;
        this.userId = LoggedUser.id();
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "meal='" + meal + '\'' +
                ", calories=" + calories +
                ", date=" + date +
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

}
