package com.raphael.philosophy;
import com.raphael.philosophy.model.user.User;
import com.raphael.philosophy.repository.UserRepository;
import com.raphael.philosophy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetAllUsers() {
        User user1 = new User("jon54", "jon@gmail.com", "xxx", "jon", "deroo", 'm', (byte) 55);
        User user2 = new User("mike22", "mike@gmail.com", "yyy", "mike", "smith", 'm', (byte) 30);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        assertEquals(2, userService.getAllUsers().size());
        verify(userRepository, times(1)).findAll();
    }


    @Test
    public void testGetUserById() {
        User user = new User("jon54", "jon@gmail.com", "xxx", "jon", "deroo", 'm', (byte) 55);
        user.setId((short) 1);
        when(userRepository.findById((short) 1)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById((short) 1);
        assertTrue(foundUser.isPresent());
        assertEquals("jon54", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById((short) 1);
    }


    @Test
    public void testCreateUser() {
        User user = new User("jon54", "jon@gmail.com", "xxx", "jon", "deroo", 'm', (byte) 55);
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("jon54", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void testUpdateUser() {
        User user = new User("jon54", "jon@gmail.com", "xxx", "jon", "deroo", 'm', (byte) 55);
        user.setId((short) 1);
        when(userRepository.existsById((short) 1)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        Optional<User> updatedUser = userService.updateUser((short) 1, user);
        assertTrue(updatedUser.isPresent());
        assertEquals("jon54", updatedUser.get().getUsername());
        verify(userRepository, times(1)).save(user);
    }

//
    @Test
    public void testDeleteUser() {
        when(userRepository.existsById((short) 1)).thenReturn(true);
        boolean result = userService.deleteUser((short) 1);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById((short) 1);
    }

}