package com.spring.Journal.Service;

import com.spring.Journal.Controller.Repository.UserRepository;
import com.spring.Journal.Entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        // Arrange
        User mockUser = new User("john","pass123");
//        mockUser.setUserName("john");
//        mockUser.setPassword("pass123");
        mockUser.setRoles(List.of("USER"));

        when(userRepository.findByUserName("john")).thenReturn(mockUser);

        // Act
        var userDetails = userDetailsService.loadUserByUsername("john");

        // Assert
        assertEquals("john", userDetails.getUsername());
        assertEquals("pass123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(userRepository.findByUserName("unknown")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown"));
    }
}
