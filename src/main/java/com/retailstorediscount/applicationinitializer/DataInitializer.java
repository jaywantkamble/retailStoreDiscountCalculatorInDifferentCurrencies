package com.retailstorediscount.applicationinitializer;

import com.retailstorediscount.entity.AppUser;
import com.retailstorediscount.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private AppUserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private static final String DUMMYUSERCREDS = "admin";

	@Override
    public void run(String... args) throws Exception {
        // Create the admin user if it doesn't exist
        if (userRepository.findByUsername(DUMMYUSERCREDS) == null) {
        	AppUser adminUser = new AppUser(DUMMYUSERCREDS, passwordEncoder.encode(DUMMYUSERCREDS)); // Hashing password value before Saving
            userRepository.save(adminUser);
        }
    }
}
