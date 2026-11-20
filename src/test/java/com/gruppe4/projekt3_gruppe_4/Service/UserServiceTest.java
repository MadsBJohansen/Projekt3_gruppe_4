package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.User;
import com.gruppe4.projekt3_gruppe_4.Repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User userWithPassword(String rawPassword) {
        User user = new User();
        user.setUsername("demo");
        user.setPasswordHash(new BCryptPasswordEncoder().encode(rawPassword));
        return user;
    }

    // ----------Login---------

    @Test
    void login_correctPassword_returnsUser() {          //Happy Flow
        when(userRepo.findByUsername("demo")).thenReturn(userWithPassword("demo"));

        User result = userService.login("demo", "demo");

        assertNotNull(result);
        assertEquals("demo", result.getUsername());


    }

    @Test
    void login_wrongPassword_returnsNull() {            //exception flow
        when(userRepo.findByUsername("demo")).thenReturn(userWithPassword("demo"));

        assertNull(userService.login("demo", "forkert"));
    }

    @Test
    void login_unknownUser_returnsNull() {
        when(userRepo.findByUsername("findesikke")).thenReturn(null);

        assertNull(userService.login("findesikke", "demo"));
    }

    //-------createUser------


    @Test
    void createUser_validInput_savesHashedPassword() {          //happy flow
        User user = new User();
        user.setUsername("ny");
        when(userRepo.findByUsername("ny")).thenReturn(null);

        assertTrue(userService.createUser(user, "hemmelig"));
        assertNotEquals("hemmelig", user.getPasswordHash());
        verify(userRepo).saveUser(user);
    }

    @Test
    void createUser_usernameTaken_returnsFalse() {          //exception flow
        User user = new User();
        user.setUsername("demo");
        when(userRepo.findByUsername("demo")).thenReturn(new User());

        assertFalse(userService.createUser(user, "demo"));
        verify(userRepo, never()).saveUser(any());
    }

    @Test
    void createUser_blankPassword_returnsFalse() {          //inputValidering
        User user = new User();
        user.setUsername("ny");

        assertFalse(userService.createUser(user, " "));
        verify(userRepo, never()).saveUser(any());
    }
}