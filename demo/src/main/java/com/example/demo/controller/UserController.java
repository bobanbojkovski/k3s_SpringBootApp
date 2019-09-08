package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value={"/users"})
public class UserController {
    @Autowired
    private UserRepository UserRepository;
    
    @GetMapping(value="/list")
    public List<User> findAll() {
        return UserRepository.findAll();
    }

    @PostMapping(value = "/create")
    public User create(@RequestBody final User user) {
        UserRepository.save(user);
        return UserRepository.findById(user.getId());
    }

}