package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> USER_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("calories_per_day", user.getCaloriesPerDay())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            deleteRoles(user);
            insertRoles(user);
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, calories_per_day=:calories_per_day, " +
                            "enabled=:enabled WHERE id=:id", map);
        }
        return user;
    }


    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    @Transactional(readOnly = true)
    public User get(int id) {
        User user = jdbcTemplate.queryForObject(
                "SELECT id, name, email, password, calories_per_day, registered, enabled FROM users WHERE id=?",
                USER_MAPPER, id);
        return setRoles(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        User user = jdbcTemplate.queryForObject(
                "SELECT id, name, email, password, calories_per_day, registered, enabled FROM users WHERE email=?",
                USER_MAPPER, email);
        return setRoles(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        final List<User> users = jdbcTemplate.query(
                "SELECT id, name, email, password, calories_per_day, registered, enabled FROM users ORDER BY name, email", USER_MAPPER);

        class UserRole {
            public UserRole(Role role, int userId) {
                this.role = role;
                this.userId = userId;
            }

            final private Role role;
            final private int userId;

            public Role getRole() {
                return role;
            }

            public int getUserId() {
                return userId;
            }
        }

        Map<Integer, List<UserRole>> userRoles = jdbcTemplate.query("SELECT role, user_id FROM user_roles",
                (rs, rowNum) -> new UserRole(Role.valueOf(rs.getString("role")), rs.getInt("user_id")))
                .stream().collect(Collectors.groupingBy(UserRole::getUserId));

        users.forEach(u -> u.setRoles(userRoles.get(u.getId()).stream().map(UserRole::getRole).collect(Collectors.toList())));
        return users;
    }

    private void insertRoles(User u) {
        Set<Role> roles = u.getRoles();
        Iterator<Role> iterator = roles.iterator();

        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, u.getId());
                        ps.setString(2, iterator.next().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    private void deleteRoles(User u) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", u.getId());
    }

    private User setRoles(User u) {
        List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles  WHERE user_id=?",
                (rs, rowNum) -> Role.valueOf(rs.getString("role")), u.getId());
        u.setRoles(roles);
        return u;
    }


}

