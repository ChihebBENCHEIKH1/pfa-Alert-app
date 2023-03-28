package com.pfa.authentication.service;

import com.pfa.authentication.dao.UserRepository;
import com.pfa.authentication.model.User;
import com.pfa.authentication.model.UserPrincaple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        UserPrincaple userPrincaple = new UserPrincaple(user);
        return userPrincaple;
    }

    public boolean ifEmailExist(String mail){
        return userRepository.existsByEmail(mail);
    }

    public User getUserByMail(String mail){
        return userRepository.findByEmail(mail);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
