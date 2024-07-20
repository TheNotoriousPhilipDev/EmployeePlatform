package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
