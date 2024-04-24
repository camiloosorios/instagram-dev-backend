package org.bosorio.instagram.dev.services;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.ws.WebServiceContext;
import org.bosorio.instagram.dev.entities.User;
import org.bosorio.instagram.dev.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@WebService(endpointInterface = "org.bosorio.instagram.dev.services.UserService")
@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordService passwordService;

    @Inject
    private JWTService jwtService;

    @Resource
    WebServiceContext webServiceContext;

    @Override
    @WebMethod
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null ) {
            throw new RuntimeException("Email or password incorrect");
        }

        user = userRepository.findUserWithPasswordById(user.getId());

        if (!passwordService.verifyPassword(password, user.getPassword())) {
            throw new RuntimeException("Email or password incorrect");
        }

        return jwtService.generateToken(user.getUsername());
    }

    @Override
    @WebMethod
    public boolean verifyToken(String token) {
        try {
            jwtService.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @WebMethod
    public String create(User user) {
        try {
            if (userRepository.findByUsername(user.getUsername()) != null) {
                throw new RuntimeException("Username already exists");
            }

            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new RuntimeException("Email already exists");
            }
            if (user.getPassword() != null) {
                String encryptedPassword = passwordService.encryptPassword(user.getPassword());
                user.setPassword(encryptedPassword);
            }
            userRepository.create(user);
            return jwtService.generateToken(user.getUsername());
        } catch (ConstraintViolationException e) {
            String errorMessage = e.getConstraintViolations().stream()
                    .map(c -> c.getPropertyPath() + ": " + c.getMessage())
                    .collect(Collectors.joining("; "));
            throw new RuntimeException(errorMessage);
        }
    }

    @Override
    @WebMethod
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @WebMethod
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @WebMethod
    public List<User> findByUsernameOrFullName(String param) {
        return userRepository.findByUsernameOrFullName(param);
    }

    @Override
    @WebMethod
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @WebMethod
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @WebMethod
    public void follow(Long follower_id, Long follow_id, String token) {
        jwtService.validateToken(token);
        userRepository.follow(follower_id, follow_id);
    }

    @Override
    @WebMethod
    public void unfollow(Long follower_id, Long follow_id, String token) {
        jwtService.validateToken(token);
        userRepository.unfollow(follower_id, follow_id);
    }

    @Override
    @WebMethod
    public List<User> findFollowing(Long userId) {
        return userRepository.findFollowing(userId);
    }

    @Override
    @WebMethod
    public List<User> findFollowers(Long userId) {
        return userRepository.findFollowers(userId);
    }

    @Override
    @WebMethod
    public void update(User user, String token) {
        jwtService.validateToken(token);
        User currentUser = userRepository.findUserWithPasswordById(user.getId());

        if (currentUser == null) {
            throw new RuntimeException("User doesn't exist");
        }

        boolean authenticated = passwordService.verifyPassword(user.getPassword(), currentUser.getPassword());

        if (!authenticated) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.getEmail().equals(currentUser.getEmail())) {
            User existingUserWithEmail = userRepository.findByEmail(user.getEmail());

            if (existingUserWithEmail != null) {
                throw new RuntimeException("Email already exists");
            }

        }

        if (!user.getUsername().equals(currentUser.getUsername())) {
            User existingUserWithUsername = userRepository.findByUsername(user.getUsername());

            if (existingUserWithUsername != null) {
                throw new RuntimeException("Username already exists");
            }
        }

        user.setCreatedAt(currentUser.getCreatedAt());
        user.setPassword(passwordService.encryptPassword(user.getPassword()));

        userRepository.update(user);

    }

    @Override
    @WebMethod
    public void delete(Long id, String token) {
        jwtService.validateToken(token);
        userRepository.delete(id);
    }

}
