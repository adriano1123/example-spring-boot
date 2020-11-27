package com.ibm.adro.learningspringboot.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ibm.adro.learningspringboot.model.User;

public interface UserDao {
    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID userUid);

    int updateUser(User user);

    int deleteUserByUserUid(UUID userUid);

    int insertUser(User user);
}
