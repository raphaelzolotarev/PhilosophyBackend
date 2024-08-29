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
        User user1 = new User("jon54", "jon@gmail.com", "xxx");
        User user2 = new User("mike22", "mike@gmail.com", "yyy");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        assertEquals(2, userService.getAllUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User("jon54", "jon@gmail.com", "xxx");
        user.setId((short) 1);
        when(userRepository.findById((short) 1)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById((short) 1);
        assertTrue(foundUser.isPresent());
        assertEquals("jon54", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById((short) 1);
    }

    @Test
    public void testCreateUser() {
        User user = new User("jon54", "jon@gmail.com", "xxx");
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("jon54", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User("jon54", "jon@gmail.com", "xxx");
        user.setId((short) 1);
        when(userRepository.existsById((short) 1)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User updatedUser = userService.updateUser(user);
        assertTrue(updatedUser != null);
        assertEquals("jon54", updatedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }


}