package com.ibm.adro.learningspringboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ibm.adro.learningspringboot.dao.FakeDataDao;
import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.model.User.Gender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao; 

    private UserService userService; 

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");

        List<User> list = new LinkedList<>();
        list.add(adrian);

        given(fakeDataDao.selectAllUsers()).willReturn(list); 

        List<User> allUsers = userService.getAllUsers();
        assertTrue(allUsers.size() == 1);

        User user = list.get(0);
        assertUsersFields(user);
    }

    @Test
    public void shouldGetUser() throws Exception {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");

        given(fakeDataDao.selectUserById(adrianUid)).willReturn(Optional.of(adrian));

        Optional<User> userOptional = userService.getUser(adrianUid);
        assertTrue(userOptional.isPresent());

        User user = userOptional.get();
        assertUsersFields(user);
    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test 
    public void removeUser() throws Exception {

    }

    @Test
    public void insertUser() throws Exception {

    }

    private void assertUsersFields(User user) {
        assertEquals(30, user.getAge());
        assertEquals("Adrián", user.getFirstName());
        assertEquals("Domínguez", user.getLastName());
        assertEquals("adrian@adro.dev", user.getEmail());
        assertEquals(Gender.MALE, user.getGender());
        assertNotNull(user.getUserUid());
    }

}