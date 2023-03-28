package com.pfa.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfa.authentication.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
