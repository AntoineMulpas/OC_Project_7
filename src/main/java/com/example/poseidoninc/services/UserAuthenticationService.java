package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.domain.UserDTO;
import com.example.poseidoninc.repositories.UserRepository;
import com.example.poseidoninc.security.CustomPasswordEncoder;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This Class is the Service layer for the User object.
 * It is annotated with the @Transaction to manage transactions of
 * all the methods contained in this class.
 */

@Service
@Transactional
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;




    public UserAuthenticationService(UserRepository userRepository, CustomPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This class overrides the method 'loadUserByUsername' to allow verification against
     * the database of the existence of a specific user using the username.
     * @param username
     * @return UserDetails filled with user's information stored in the database.
     * @throws UsernameNotFoundException
     */

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

    /**
     * This method is used to save a new user.
     * Firstly, it verifies if the information are valid.
     * Secondly it verifies that no username passed as parameter already exists.
     * If it is the case, the method throws an IllegalArgumentException.
     * Otherwise it procedes to save the user.
     * @param user
     * @return the saved user.
     */

    public User saveAUser(UserDTO user) {
        validateUser(user);
        if (userRepository.findByUsernameEquals(user.getUsername()).isPresent()) {
            //logger.error("Username already exists for " + user);
            throw new IllegalArgumentException("Username already used.");
        }

        User userToSave = new User(
                user.getUsername(),
                passwordEncoder.encodePassword(user.getPassword()),
                user.getFullName(),
                user.getRole()
        );
        //logger.info("User: " + user.getUsername() + " has been saved successfully.");
        return userRepository.save(userToSave);
    }

    /**
     * This private method is used to verify that the information
     * concerning the user are correct.
     * The username and password should not be null nor be empty.
     *
     * @param user
     */

    private void validateUser(UserDTO user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            //logger.error("Username cannot been empty when saving a user.");
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            //logger.error("Password cannot been empty when saving a user.");
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    /**
     * This method is used to retrieve all users.
     * @return List of all users.
     */

    public List <User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This method is used to retrieve a specific user by id.
     * @param id
     * @return user or null if not found.
     */

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * This method is used to delete a specific user by id.
     * If the user is not found in the database, the method will return false.
     * @param id
     * @return a boolean depending on the outcome of the operation.
     */

    public boolean deleteUserById(Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    /**
     * This method is used to update a specific user.
     * If the user to be updated is not found in the database,
     * the method will return null.
     * Otherwise, it proceeds to update the user and returns it.
     * @param id
     * @param user
     * @return user or null.
     */
    public User updateUser(Integer id, UserDTO user) {
        Optional <User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            optionalUser.get().setFullname(user.getFullName());
            optionalUser.get().setPassword(passwordEncoder.encodePassword(user.getPassword()));
            optionalUser.get().setRole(user.getRole());
            return userRepository.save(optionalUser.get());
        }
        return null;
    }

}
