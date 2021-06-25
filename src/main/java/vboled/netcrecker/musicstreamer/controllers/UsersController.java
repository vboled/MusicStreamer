package vboled.netcrecker.musicstreamer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vboled.netcrecker.musicstreamer.dao.UserDAO;
import vboled.netcrecker.musicstreamer.models.User;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDAO userDAO;

    @Autowired
    UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userDAO.getAllUsers());
        return "users/allUsers";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDAO.getUserById(id));
        return "users/user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id")int id, Model model) {
        model.addAttribute("user", userDAO.getUserById(id));
        return "users/editUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userDAO.addUser(user);
        return "redirect:/users";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") int id) {
        userDAO.updateUser(id, user);
        return "redirect/users";
    }

}
