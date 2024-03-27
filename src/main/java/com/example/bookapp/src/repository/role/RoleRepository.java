package com.example.bookapp.src.repository.role;

import com.example.bookapp.src.model.Role;
import com.example.bookapp.src.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(User.RoleName name);
}
