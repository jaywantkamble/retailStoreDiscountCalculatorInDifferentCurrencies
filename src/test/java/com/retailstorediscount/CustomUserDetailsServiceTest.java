package com.retailstorediscount;

import com.retailstorediscount.entity.AppUser;
import com.retailstorediscount.repository.AppUserRepository;
import com.retailstorediscount.service.CustomUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private AppUserRepository userRepository;

    private final String username = "testUser";
    private final String password = "testPassword";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        AppUser appUser = new AppUser(username,password);
       
        when(userRepository.findByUsername(username)).thenReturn(appUser);

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword()); // Note: The password will be encoded in a real scenario
        assertTrue(userDetails.getAuthorities().isEmpty()); // Assuming no roles/authorities are set
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist_ThrowsUsernameNotFoundException() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(username);
        });
    }
}
