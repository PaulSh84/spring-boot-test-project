package email.course.springboottestproject;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> list = new ArrayList<>();

    public List<User> getAllUsers() {
        return list;
    }

    public void storeNewUser(User user) {
    }

    public User getUserByUsername(String username) {
        return null;
    }
}
