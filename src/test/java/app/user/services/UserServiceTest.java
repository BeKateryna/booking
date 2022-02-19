package app.user.services;

import app.user.Dao.UserDao;
import app.user.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService service;
    private UserDao mockDao;

    @BeforeEach
    public void init() throws IOException {
        mockDao = mock(UserDao.class);
        service = new UserService(mockDao);
        UserModel userModel = new UserModel();
        userModel.setId("8");
        userModel.setFirstName("Lola");
        userModel.setLastName("Lolovich");
        when(mockDao.createUser(userModel)).thenReturn(userModel);
        when(mockDao.findUserByFullName("Lola", "Lolovich")).thenReturn(userModel);
    }
    @Test
    public void should_createUser_when_given_correct_data() throws IOException {
        service.createUser("Lola", "Lolovich");
        verify(mockDao, atLeast(1)).createUser(any());
    }

    @Test
    public void should_returnCorrect_user_ifExist_inDB_by_fullName() throws IOException {
        String userFirstname = "Lola";
        String userLastname = "Lolovich";
        UserModel userModel = new UserModel();
        userModel.setFirstName(userFirstname);
        userModel.setLastName(userLastname);
        userModel.setId("8");
        UserModel user = service.findUserByFullName(userFirstname, userLastname);
        Assertions.assertEquals(userModel, user);
    }
}