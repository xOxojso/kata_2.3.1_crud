package crud.controller;

import crud.model.User;
import crud.service.UserService;
import crud.valid.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable(value = "id") long id, ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("user") @Valid User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "users/new";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }


    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String updateUserById(@ModelAttribute("user") @Valid User user, BindingResult result,
                                 @PathVariable("id") long id) {
        if (result.hasErrors()) {
            return "users/edit";
        }
        userService.updateUserById(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
