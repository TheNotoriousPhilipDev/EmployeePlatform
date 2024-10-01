package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.entities.User;
import com.agomez.backendapp.employemanagementback.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }
}
