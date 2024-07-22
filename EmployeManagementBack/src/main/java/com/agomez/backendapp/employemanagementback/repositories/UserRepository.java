package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
