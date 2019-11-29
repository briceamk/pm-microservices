package cm.gelodia.uaa.service.impl;

import cm.gelodia.uaa.constant.ConstantTypes;
import cm.gelodia.uaa.exception.ConflictException;
import cm.gelodia.uaa.exception.ResourceNotFoundException;
import cm.gelodia.uaa.model.Role;
import cm.gelodia.uaa.model.User;
import cm.gelodia.uaa.repository.UserRepository;
import cm.gelodia.uaa.service.RoleService;
import cm.gelodia.uaa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleservice;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleservice = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User add(User user) {
        // check if user exist with same email or username
        if(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail()))
            throw new ConflictException("Username or email is taken");
        // encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //set default role ROLE_USER to new user
        Role role = roleservice.getByName(ConstantTypes.DEFAULT_USER_ROLE);
        user.setRoles(new LinkedHashSet<>(Arrays.asList(role)));
        //TODO publish event USER_CREATED to the email-notification-service
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        log.info("User in edit mode: {}", user.toString());
        //TODO manage unique email en username on edit mode
        return userRepository.save(user);
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id '" + id + "' not found")
        );
    }

    @Override
    public Page<User> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return userRepository.findAll(pageable);
    }

    @Override
    public void delete(String id) {
        //TODO
    }

    @Override
    public void deleteAll(List<String> ids) {
        //TODO
    }

}
