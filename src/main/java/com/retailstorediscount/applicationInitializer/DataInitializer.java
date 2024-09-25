package com.retailstorediscount.applicationInitializer;

import com.retailstorediscount.entity.AppUser;
import com.retailstorediscount.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private AppUserRepository userRepository;

    private PasswordEncoder passwordEncoder;
   
    public DataInitializer(AppUserRepository userRepository,PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
    public void run(String... args) throws Exception {
        // Create the admin user if it doesn't exist
        if (userRepository.findByUsername("admin") == null) {
        	AppUser adminUser = new AppUser("admin", passwordEncoder.encode("admin")); // Hashing value
            userRepository.save(adminUser);
        }
    }
}
