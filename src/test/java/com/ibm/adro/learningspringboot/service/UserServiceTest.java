package com.ibm.adro.learningspringboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ibm.adro.learningspringboot.dao.FakeDataDao;
import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.model.User.Gender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

        List<User> allUsers = userService.getAllUsers(Optional.empty());
        assertTrue(allUsers.size() == 1);

        User user = list.get(0);
        assertAdrianUsersFields(user);
    }

    @Test
    public void shouldGetAllUsersByGender() {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");

        UUID mariaUid = UUID.randomUUID();
        User maria = new User(mariaUid, "María", "Conchita", Gender.FEMALE, 30, "mariaconchita.alonso@adro.dev");

        List<User> list = new LinkedList<>();
        list.add(adrian);
        list.add(maria);

        given(fakeDataDao.selectAllUsers()).willReturn(list); 

        List<User> filteredUsers = userService.getAllUsers(Optional.of("MALE"));
        assertTrue(filteredUsers.size() == 1);

        User shouldBeJustAdrian = filteredUsers.get(0);
        assertAdrianUsersFields(shouldBeJustAdrian);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidGender() {
        assertThrows(IllegalStateException.class, () -> userService.getAllUsers(Optional.of("Mala")));
    }

    @Test
    public void shouldGetUser() throws Exception {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");

        given(fakeDataDao.selectUserById(adrianUid)).willReturn(Optional.of(adrian));

        Optional<User> userOptional = userService.getUser(adrianUid);
        assertTrue(userOptional.isPresent());

        User user = userOptional.get();
        assertAdrianUsersFields(user);
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");

        given(fakeDataDao.selectUserById(adrianUid)).willReturn(Optional.of(adrian));
        given(fakeDataDao.updateUser(adrian)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateRestult = userService.updateUser(adrian);

        verify(fakeDataDao).selectUserById(adrianUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue(); 
        
        assertAdrianUsersFields(user);
        assertEquals(updateRestult, 1);
    }

    @Test 
    public void shouldRemoveUser() throws Exception {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");

        given(fakeDataDao.selectUserById(adrianUid)).willReturn(Optional.of(adrian));
        given(fakeDataDao.deleteUserByUserUid(adrianUid)).willReturn(1);

        int deleteRestult = userService.removeUser(adrianUid);

        verify(fakeDataDao).selectUserById(adrianUid);
        verify(fakeDataDao).deleteUserByUserUid(adrianUid);
        
        assertEquals(deleteRestult, 1);
    }

    @Test
    public void shouldInsertUser() throws Exception {
        UUID adrianUid = UUID.randomUUID();
        User adrian = new User(adrianUid, "Adrián", "Domínguez", Gender.MALE, 30, "adrian@adro.dev");
        
        given(fakeDataDao.insertUser(adrian)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(adrian); 

        verify(fakeDataDao).insertUser(captor.capture());

        User user = captor.getValue();

        assertAdrianUsersFields(user);
        assertEquals(1, insertResult);
    }

    private void assertAdrianUsersFields(User user) {
        assertEquals(30, user.getAge());
        assertEquals("Adrián", user.getFirstName());
        assertEquals("Domínguez", user.getLastName());
        assertEquals("adrian@adro.dev", user.getEmail());
        assertEquals(Gender.MALE, user.getGender());
        assertNotNull(user.getUserUid());
    }

}
