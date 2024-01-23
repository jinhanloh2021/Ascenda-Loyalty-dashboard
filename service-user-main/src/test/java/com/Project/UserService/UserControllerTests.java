package com.Project.UserService;

import com.Project.UserService.Controller.UserController;
import com.Project.UserService.Model.User;
import com.Project.UserService.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.Project.UserService.Fakes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testCreateUser() {
        //Arrange
        User user = fakeUser;

        //Act
        ResponseEntity<?> response = userController.createUser(user);

        //Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).createUser(eq(user));
    }

    @Test
    void getAllUsers() {
        //Arrange
        List<User> mockUserList = List.of(fakeUser);
        when(userService.getAllUsers()).thenReturn(mockUserList);

        //Act
        ResponseEntity<List<User>> response = userController.getUsers();

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserList, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUser() {
        //Arrange
        String userId = fakeUserId;
        User mockUser = fakeUser;
        when(userService.getUser(userId)).thenReturn(mockUser);

        //Act
        ResponseEntity<User> response = userController.getUserById(userId);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getUser(eq(userId));
    }

    @Test
    void testUpdateUser() {
        //Arrange
        String userId = fakeUserId;
        User newUser = fakeUser;
        User mockUpdatedUser = fakeUser;
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(mockUpdatedUser);

        //Act
        ResponseEntity<User> response = userController.updateUser(userId, newUser);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUpdatedUser, response.getBody());
        verify(userService, times(1)).updateUser(eq(userId), eq(newUser));
    }

    @Test
    void testDeleteUser() {
        //Arrange
        String userId = fakeUserId;
        User mockDeleteUser = fakeUser;
        when(userService.deleteUser(userId)).thenReturn(mockDeleteUser);

        //Act
        ResponseEntity<User> response = userController.deleteUser(userId);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDeleteUser, response.getBody());
        verify(userService, times(1)).deleteUser(eq(userId));
    }


}

final class Fakes {
    static final String fakeUserId = "fakeUserId";

    static final String name = "fake user";

    static final String email = "fakeuser@email.com";

    static final String role = "admin";

    static final User fakeUser = new User();
}
