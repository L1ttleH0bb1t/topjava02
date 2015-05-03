package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

/**
 * GKislin
 * 01.05.2015.
 */
public class UserUtil {

    public static User createFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail().toLowerCase(),
                PasswordUtil.encode(newUser.getPassword()), true, Role.ROLE_USER);
    }

    public static UserTo asTo(AbstractUser user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail());
    }

    public static User updateFromTo(User oldUser, UserTo updatedUser) {
        return updateInternal(oldUser, updatedUser);
    }

    public static User update(User oldUser, User updatedUser) {
        oldUser.setRoles(updatedUser.getRoles());
        oldUser.setEnabled(updatedUser.isEnabled());
        return updateInternal(oldUser, updatedUser);
    }

    public static <T extends AbstractUser> T encode(T u) {
        u.setPassword(PasswordUtil.encode(u.getPassword()));
        return u;
    }

    private static User updateInternal(User oldUser, AbstractUser updatedUser) {
        String password = updatedUser.getPassword();
        if (password != null) {
            oldUser.setPassword(PasswordUtil.encode(password));
        }
        oldUser.setName(updatedUser.getName());
        oldUser.setEmail(updatedUser.getEmail().toLowerCase());
        return oldUser;
    }
}
