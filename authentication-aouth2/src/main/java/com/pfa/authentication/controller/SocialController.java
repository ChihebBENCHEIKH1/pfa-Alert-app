package com.pfa.authentication.controller;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tomcat.util.http.parser.AcceptEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pfa.authentication.dto.JwtLogin;
import com.pfa.authentication.dto.LoginResponse;
import com.pfa.authentication.dto.TokenDto;
import com.pfa.authentication.model.Role;
import com.pfa.authentication.model.User;
import com.pfa.authentication.service.RoleService;
import com.pfa.authentication.service.TokenService;
import com.pfa.authentication.service.UserService;

// http://localhost:8080
@RestController
@RequestMapping("/social")
@CrossOrigin("*")
//http://localhost:8080/social
public class SocialController {

    private UserService userService;

    private RoleService roleService;

    private TokenService tokenService;

    private PasswordEncoder passwordEncoder;

    private String email;


    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;

    @Autowired
    public SocialController(UserService userService,RoleService roleService,TokenService tokenService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    //http://localhost:8080/social/google
    @PostMapping(value = "/google")
    public ResponseEntity<Object> loginWithGoogle(@RequestBody TokenDto tokenDto) throws Exception {
        System.out.println("pass " + password);
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport,factory)
                        .setAudience(Collections.singleton(idClient));

        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(),tokenDto.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        email = payload.getEmail();
        User user = new User();
        if(userService.ifEmailExist(email)){
            user = userService.getUserByMail(email);
        } else {
            user = createUser(email);
        }
        ///////////////////////////
        JwtLogin jwtLogin = new JwtLogin();
        jwtLogin.setEmail(user.getEmail());
        jwtLogin.setPassword(password);
        LoginResponse loginResponse=tokenService.login(jwtLogin);
        ///////////////////////////
        return new ResponseEntity<Object>(loginResponse, HttpStatus.OK);
    }

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        List<Role> roles = roleService.getRoles();
        user.getRoles().add(roles.get(0));
        return userService.saveUser(user);
    }

    //http://localhost:8080/social/facebook
    @PostMapping("/facebook")
    public ResponseEntity<Object> loginWithFacebook(@RequestBody TokenDto tokenDto) throws Exception {
        Facebook facebook = new FacebookTemplate(tokenDto.getToken());
        String [] data = {"email"};
        org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class,data);

        email = user.getEmail();
        User userFace = new User();
        if(userService.ifEmailExist(email)){
            userFace = userService.getUserByMail(email);
        } else {
            userFace = createUser(email);
        }
        ///////////////////////////
        JwtLogin jwtLogin = new JwtLogin();
        jwtLogin.setEmail(user.getEmail());
        jwtLogin.setPassword(password);
        ///////////////////////////

        return new ResponseEntity<Object>(tokenService.login(jwtLogin), HttpStatus.OK);
    }


}
