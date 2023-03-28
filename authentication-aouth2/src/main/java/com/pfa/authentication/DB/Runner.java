package com.pfa.authentication.DB;

import com.pfa.authentication.dao.RoleRepository;
import com.pfa.authentication.dao.UserRepository;
import com.pfa.authentication.model.Role;
import com.pfa.authentication.model.User;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*@Service
public class Runner  implements CommandLineRunner{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public Runner(UserRepository userRepository, PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository=roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
    	roleRepository.save(new Role(1L,"admin",new HashSet<User>().add(userRepository.findById(1L))));
        User user = new User();
        user.setEmail("chiheb@gmail.com");
        user.setPassword(passwordEncoder.encode("chiheb1234"));
        userRepository.save(user);
        User user1 = new User();
        user1.setEmail("ahmed@gmail.com");
        user1.setPassword(passwordEncoder.encode("ahmed123"));
        userRepository.save(user1);
    }
}
*/