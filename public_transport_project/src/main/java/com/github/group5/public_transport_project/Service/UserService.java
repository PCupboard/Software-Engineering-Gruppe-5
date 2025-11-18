package com.github.group5.public_transport_project.Service;

import com.github.group5.public_transport_project.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users = new ArrayList<>();

    public boolean register(String username, String password, String email) {
        if (getUserByUsername(email) != null) {
            return false;
        }
        users.add(new User(username, password, email));
        return true;
    }

    public User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername() == username) {
                return u;
            }
        }
        return null;
    }
}

