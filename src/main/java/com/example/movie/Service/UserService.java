package com.example.movie.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.Model.User;
import com.example.movie.Repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(User user) throws Exception {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username already exists");
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }
        
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public User updateUser(User user) throws Exception {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            throw new Exception("User not found");
        }
        
        // Check if username is being changed and new username already exists
        if (!existingUser.getUsername().equals(user.getUsername()) 
            && userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username already exists");
        }
        
        // Check if email is being changed and new email already exists
        if (!existingUser.getEmail().equals(user.getEmail()) 
            && userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }
        
        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws Exception {
        if (!userRepository.existsById(id)) {
            throw new Exception("User not found");
        }
        userRepository.deleteById(id);
    }
}