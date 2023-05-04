package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.repositories.UserRepository;
import com.example.poseidoninc.security.CustomPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository        userRepository;
    private final CustomPasswordEncoder passwordEncoder;


    public UserAuthenticationService(UserRepository userRepository, CustomPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> optionalAuthUser = userRepository.findByUsernameEquals(username);
        if (optionalAuthUser.isPresent()) {
            return new User(
                    optionalAuthUser.get().getUsername(),
                    optionalAuthUser.get().getPassword(),
                    optionalAuthUser.get().getFullname(),
                    optionalAuthUser.get().getRole()
            );
        }
        throw new IllegalArgumentException("Username or password are not correct.");
    }


    public User saveAUser(User user) {
        validateUser(user);
        if (userRepository.findByUsernameEquals(user.getUsername()).isPresent()) {
            //logger.error("Username already exists for " + user);
            throw new RuntimeException("Username already used.");
        }

        User userToSave = new User(
                user.getUsername(),
                passwordEncoder.passwordEncoder().encode(user.getPassword()),
                user.getFullname(),
                user.getRole()
        );
        //logger.info("User: " + user.getUsername() + " has been saved successfully.");
        return userRepository.save(userToSave);
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            //logger.error("Username cannot been empty when saving a user.");
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            //logger.error("Password cannot been empty when saving a user.");
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    public List <User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }


    public User updateUser(Integer id, User user) {
        Optional <User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            optionalUser.get().setFullname(user.getFullname());
            optionalUser.get().setPassword(user.getPassword());
            optionalUser.get().setRole(user.getRole());
            return userRepository.save(optionalUser.get());
        }
        return null;
    }

}
