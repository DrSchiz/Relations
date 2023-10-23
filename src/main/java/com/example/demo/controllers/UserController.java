package com.example.demo.controllers;

import com.example.demo.models.Cat;
import com.example.demo.models.Passport;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.CatRepository;
import com.example.demo.repositories.PassportRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Iterable<Cat> cats = catRepository.findAll();
        model.addAttribute("cats", cats);
        Iterable<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "all-users";
    }

    @RequestMapping(value="/create-user", method = RequestMethod.GET)
    public String getCreate(@ModelAttribute("user") User user, Model model) {
        Iterable<Passport> passports = passportRepository.findAll();
        model.addAttribute("passports", passports);
        return "create-user";
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String postCreate(@ModelAttribute("user") User user, @RequestParam(name = "number") String number, Model model) {
        user.setPassport(passportRepository.findByNumber(number));
        userRepository.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.GET)
    public String getRoleAdd(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Iterable<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "add-role";
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public String postRoleUser(@RequestParam int user, @RequestParam int role, Model model) {
        User _user = userRepository.findById(user).orElseThrow();
        Role _role = roleRepository.findById(role).orElseThrow();
        _user.getRoles().add(_role);
        userRepository.save(_user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/cat/add", method = RequestMethod.GET)
    public String getCatAdd(@ModelAttribute("cat") Cat cat, Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "add-cat";
    }

    @RequestMapping(value = "/cat/add", method = RequestMethod.POST)
    public String postCat(@RequestParam int user, @RequestParam String cat, Model model) {
        User _user = userRepository.findById(user).orElseThrow();
        Cat _cat = new Cat();
        _cat.setName(cat);
        _cat.setUser(_user);
        catRepository.save(_cat);
        return "redirect:/users";
    }
}