package com.example.cryptoapp.services;

import com.example.cryptoapp.exceptions.ValidationException;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.repositories.IRepository;

import java.util.List;

public class UserService {
    private IRepository<User> userRepository;

    public UserService(IRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) throws ValidationException {
        validateUser(user);
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) throws ValidationException {
        validateUser(user);
        userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    private void validateUser(User user) throws ValidationException {
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new ValidationException("User first name cannot be empty.");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ValidationException("User lastname cannot be empty.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("User email cannot be empty.");
        }

        if (isEmailAlreadyExists(user.getEmail())) {
            throw new ValidationException("User with this email already exists.");
        }
    }

    private boolean isEmailAlreadyExists(String email) {
        List<User> existingUsers = userRepository.findAll();
        return existingUsers.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

}