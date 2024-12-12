package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    public User getUserById(int user_Id) {
        return userRepository.findById(user_Id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Find user by username
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println("findByUsername returned: " + (user != null ? user.getUsername() : "null")); // Debug log
        return user;
    }


    public User updateUser(String username, User updatedUser) {
        User user = findByUsername(username);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setCompanyName(updatedUser.getCompanyName());
            user.setCompanyDescription(updatedUser.getCompanyDescription());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public void deleteUser(int user_Id) {
        System.out.println("Deleting user with ID: " + user_Id);
        userRepository.deleteById(user_Id);
    }
}
