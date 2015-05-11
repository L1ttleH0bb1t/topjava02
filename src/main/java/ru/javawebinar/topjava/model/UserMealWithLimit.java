package ru.javawebinar.topjava.model;

/**
 * Created by eugene on 11.05.15.
 */
public class UserMealWithLimit extends UserMeal {

    private boolean limitExceeded;

    public UserMealWithLimit(UserMeal meal, boolean limitExceeded) {
        super(meal);
        this.limitExceeded = limitExceeded;
    }

    public boolean isLimitExceeded() {
        return limitExceeded;
    }

    public void setLimitExceeded(boolean limitExceeded) {
        this.limitExceeded = limitExceeded;
    }
}
