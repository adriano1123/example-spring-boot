package com.ibm.adro.learningspringboot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ibm.adro.learningspringboot.dao.UserDao;
import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.model.User.Gender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao; 

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();  
        if(gender.isEmpty()) {
            return users; 
        }
        try {
            Gender theGender = Gender.valueOf(gender.get().toUpperCase());
            return users.stream()
            .filter(user -> user.getGender().equals(theGender))
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Gender", e); 
        }
    }

    public Optional<User> getUser(UUID userUid) {
         return userDao.selectUserById(userUid);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if(optionalUser.isPresent()) {
            userDao.updateUser(user);
            return 1;
        } 
        return -1;
    }

    public int removeUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if(optionalUser.isPresent()) {
            userDao.deleteUserByUserUid(userUid);
            return 1;
        }
        return -1;
    }

    public int insertUser(User user) {
        userDao.insertUser(user);
        return 1;
    }
}
