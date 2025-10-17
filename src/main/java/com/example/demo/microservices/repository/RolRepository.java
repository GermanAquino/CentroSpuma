package com.example.demo.microservices.repository;

import com.example.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Role,Long> {}
