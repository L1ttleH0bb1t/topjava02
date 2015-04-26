package ru.javawebinar.topjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import ru.javawebinar.topjava.util.DateConverter;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * GKislin
 * 06.03.2015.
 */
@Entity
@Table(name = "MEALS")
@NamedQueries({
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT um FROM UserMeal um WHERE um.user.id=?1 ORDER BY um.date DESC"),
        @NamedQuery(name = UserMeal.FILTER_BY_DATE, query = "SELECT um FROM UserMeal um WHERE um.user.id=?1 and um.date >= ?2 and um.date <= ?3 ORDER BY um.date DESC"),
        @NamedQuery(name = UserMeal.GET_MEAL, query = "SELECT um FROM UserMeal um WHERE um.id = ?1 and um.user.id = ?2"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=?1 and um.user.id = ?2"),
        @NamedQuery(name = UserMeal.DELETE_ALL, query = "DELETE FROM UserMeal um WHERE um.user.id = ?1"),
})
public class UserMeal extends BaseEntity {

    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String FILTER_BY_DATE = "UserMeal.filterByDate";
    public static final String GET_MEAL = "UserMeal.getMeal";
    public static final String DELETE = "UserMeal.delete";
    public static final String DELETE_ALL = "UserMeal.deleteAll";

    @Column(name = "meal", nullable = false)
    @Length(max = 200)
    protected String meal;

    @Column(name = "calories", nullable = false)
    protected Short calories;

    @Column(name = "date", nullable = false)
    @Convert(converter = DateConverter.class)
    protected LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public UserMeal() {

    }

    public UserMeal(UserMeal meal) {
        this(meal.getId(), meal.getMeal(), meal.getCalories(), meal.getDate());
    }

    public UserMeal(Integer id, String meal, Short calories, LocalDateTime date) {
        this.id = id;
        this.meal = meal;
        this.calories = calories;
        this.date = date;
    }

    public UserMeal(Integer id, String meal, Short calories, LocalDateTime date, User user) {
        this.id = id;
        this.meal = meal;
        this.calories = calories;
        this.date = date;
        this.user = user;
    }

    public UserMeal(String meal, Short calories, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
