package com.ibm.adro.learningspringboot.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.model.User.Gender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FakeDataDaoTest {

    private FakeDataDao fakeDataDao; 

    @BeforeEach
    public void setUp() throws Exception {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    public void shouldSelectAllUsers() throws Exception {
        List<User> users = fakeDataDao.selectAllUsers();
        assertEquals(1, users.size());
        User user = users.get(0); 
        assertEquals(30, user.getAge());
        assertEquals("Dominguez", user.getLastName());
        assertEquals("Adrian", user.getFirstName());
    }

    @Test
    public void shouldSelectUserByUserUid() throws Exception {
        UUID clorofrormoUserUid = UUID.randomUUID(); 
        User user = new User(clorofrormoUserUid, "Cloroformo", "Tristachi", Gender.FEMALE, 30, "cloroformo@gmail.com");
        fakeDataDao.insertUser(user);
        
        assertEquals(2, fakeDataDao.selectAllUsers().size());
    }

    @Test
    public void updateUser() throws Exception {
        UUID adrianUserUid =  fakeDataDao.selectAllUsers().get(0).getUserUid();
        User newAdrianUser = new User(adrianUserUid, "Cloroformo", "Tristachi", Gender.FEMALE, 30, "cloroformo@gmail.com");
        fakeDataDao.updateUser(newAdrianUser); 
        
        Optional<User> user = fakeDataDao.selectUserById(adrianUserUid); 
        assertTrue(user.isPresent());
        assertEquals("Tristachi", user.get().getLastName());
    }

    @Test
    public void deleteUserByUserUid() throws Exception {

    }

    @Test
    public void insertUser() throws Exception {

    }
}
