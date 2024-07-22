package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
