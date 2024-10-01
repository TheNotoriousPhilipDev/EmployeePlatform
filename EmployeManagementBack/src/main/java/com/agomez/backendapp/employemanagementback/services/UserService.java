package com.agomez.backendapp.employemanagementback.services;


import com.agomez.backendapp.employemanagementback.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);
}
