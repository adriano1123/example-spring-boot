package com.ibm.adro.learningspringboot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ibm.adro.learningspringboot.dao.UserDao;
import com.ibm.adro.learningspringboot.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao; 

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers(); 
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
