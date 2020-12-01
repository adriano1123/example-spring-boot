package com.ibm.adro.learningspringboot.resource;

import java.util.List;

import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
    
    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> fetchUsers() {
        return userService.getAllUsers(); 
    } 

}
