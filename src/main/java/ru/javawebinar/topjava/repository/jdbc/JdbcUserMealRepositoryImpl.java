package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUserMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUserMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("meal", userMeal.getMeal())
                .addValue("calories", userMeal.getCalories())
                .addValue("date", userMeal.getDate())
                .addValue("user_id", userId);
        if (userMeal.isNew()) {
            Number newId = insertUserMeal.executeAndReturnKey(map);
            userMeal.setId(newId.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET meal=:meal, calories=:calories, date=:date, " +
                    "user_id=:user_id", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? and user_id=?", id, userId) != 0;

    }

    @Override
    public boolean deleteAll(int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE user_id=?", userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return jdbcTemplate.queryForObject(
                "SELECT id, meal, calories, date, user_id FROM meals " +
                        "WHERE id = ? and user_id = ?", ROW_MAPPER, id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT id, meal, calories, date, user_id FROM meals " +
                        "WHERE user_id=?", ROW_MAPPER, userId);
    }

    @Override
    public List<UserMeal> filterByDate(Date start, Date end, int userId) {
        return jdbcTemplate.query(
                "SELECT id, meal, calories, date, user_id FROM meals " +
                        "WHERE date >= ? and date <= ? and user_id = ?",
                ROW_MAPPER, start, end, userId
        );
    }
}
