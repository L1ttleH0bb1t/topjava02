package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import ru.javawebinar.topjava.util.AbstractUser;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo implements AbstractUser, Serializable {
    protected int id;

    public UserTo() {
    }

    public UserTo(int id, String name, String email, short caloriesPerDay) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.caloriesPerDay = caloriesPerDay;
    }

    @NotEmpty
    protected String name;

    @Email
    protected String email;

    @Size(min = 5, max = 15, message = " must between 5 and 15 characters")
    protected String password;

    protected short caloriesPerDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = (id == null ? 0 : id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCaloriesPerDay(Short caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Short getCaloriesPerDay() { return caloriesPerDay; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", caloriesPerDay=" + caloriesPerDay + '\'' +
                '}';
    }
}
