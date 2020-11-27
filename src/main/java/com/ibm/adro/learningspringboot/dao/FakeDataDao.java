package com.ibm.adro.learningspringboot.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.model.User.Gender;

import org.springframework.stereotype.Repository;

@Repository
public class FakeDataDao implements UserDao {

    private Map<UUID, User> database;

    public FakeDataDao() {
        database = new HashMap<>();
        UUID adrianUserUid = UUID.randomUUID();
        database.put(adrianUserUid,
                new User(adrianUserUid, "Adrian", "Dominguez", Gender.MALE, 30, "adrian.dominguez1@ibm.com"));
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserById(UUID userUid) {
        return Optional.ofNullable(database.get(userUid));
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }

    @Override
    public int deleteUserByUserUid(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int insertUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }

}
