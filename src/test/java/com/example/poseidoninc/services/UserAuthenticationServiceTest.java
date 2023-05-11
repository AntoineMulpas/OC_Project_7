package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.repositories.UserRepository;
import com.example.poseidoninc.security.CustomPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {

    private UserAuthenticationService underTest;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomPasswordEncoder customPasswordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        underTest = new UserAuthenticationService(userRepository, customPasswordEncoder);
        user = new User(1, "antoine", "password", "antoine", "ADMIN");
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByUsernameEquals(any())).thenReturn(Optional.of(user));
        assertEquals("antoine", underTest.loadUserByUsername(anyString()).getUsername());
    }

    @Test
    void loadUserByUsernameShouldThrow() {
        when(userRepository.findByUsernameEquals(any())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> underTest.loadUserByUsername(anyString()));
    }

    @Test
    void saveAUser() {
        when(customPasswordEncoder.encodePassword((anyString()))).thenReturn("password");
        when(userRepository.findByUsernameEquals(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        assertNotNull(underTest.saveAUser(user).getUsername());
    }

    @Test
    void saveAUserShouldThrowIfUsernameIsNull() {
     assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(new User(null, "password", "antoine", "ADMIN")));
    }

    @Test
    void saveAUserShouldThrowIfUsernameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(new User("", "password", "antoine", "ADMIN")));
    }

    @Test
    void saveAUserShouldThrowIfPasswordIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(new User("antoine", "", "antoine", "ADMIN")));
    }

    @Test
    void saveAUserShouldThrowIfPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> underTest.saveAUser(new User("antoine", null, "antoine", "ADMIN")));
    }

    @Test
    void saveAUserShouldThrowIfUserAlreadyExists() {
       when(userRepository.findByUsernameEquals(anyString())).thenReturn(Optional.of(new User()));
       assertThrows(RuntimeException.class, () -> underTest.saveAUser(new User("antoine", "password", "antoine", "ADMIN")));
    }

    @Test
    void findAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        assertEquals(2, underTest.findAllUsers().size());
    }

    @Test
    void findUserById() {
        User user1 = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        assertEquals(user1, underTest.findUserById(1));
    }

    @Test
    void findUserByIdShouldReturnNull() {
        User user1 = new User();
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.findUserById(1));
    }

    @Test
    void deleteUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        assertTrue(underTest.deleteUserById(1));
    }

    @Test
    void deleteUserByIdShouldReturnFalse() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(underTest.deleteUserById(1));
    }

    @Test
    void updateUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user.getFullname(), underTest.updateUser(1, user).getFullname());
    }

    @Test
    void updateUserShouldReturnNull() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.updateUser(1, user));
    }
}