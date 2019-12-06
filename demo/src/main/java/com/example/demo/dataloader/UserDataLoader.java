package com.example.demo.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements ApplicationRunner {

    private UserRepository UserRepository;

    @Autowired
    public UserDataLoader(UserRepository UserRepository) {
        this.UserRepository=UserRepository;
    }

    public void run(ApplicationArguments args) {
        boolean defaultUser = UserRepository.existsById(1);
        if (!defaultUser) {
            UserRepository.save(new User("janne_suomalainen", "AYck8WkIwhZclxRIklc2VmzQ9K2tqrdH"));
        } 
    }
}