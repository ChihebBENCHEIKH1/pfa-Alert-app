package com.pfa.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfa.authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

    public boolean existsByEmail(String email);
}
