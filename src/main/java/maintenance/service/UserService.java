package com.drivego.maintenance.service;

import com.drivego.maintenance.model.Role;
import com.drivego.maintenance.model.User;
import com.drivego.maintenance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public User save(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public void createDefaultUsers() {
        // Create admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", "admin@drivego.com", "admin123", Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(admin);
        }
        
        // Create regular user if it doesn't exist
        if (!userRepository.existsByUsername("user")) {
            User user = new User("user", "user@drivego.com", "user123", Role.USER);
            user.setPassword(passwordEncoder.encode("user123"));
            userRepository.save(user);
        }
    }
}
