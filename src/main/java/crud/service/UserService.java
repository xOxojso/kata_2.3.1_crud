package crud.service;

import crud.model.User;
import crud.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRep userRep;

    @Autowired
    public UserService(UserRep userRep) {
        this.userRep = userRep;
    }

    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    public User getUserById(long id) {
        return userRep.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveUser(User user) {
        userRep.save(user);
    }

    public void deleteById(long id) {
        userRep.deleteById(id);
    }

    public void updateUserById(long id, User updateUser) {
        updateUser.setId(id);
        userRep.save(updateUser);
    }

    public Optional<User> findUserByName(String name) {
        return userRep.findByName(name);
    }

}
