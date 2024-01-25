package com.filmrate.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    private static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMD5Hash(String input) {
        md.update(input.getBytes());
        byte[] digest = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b & 0xff));
        }
        return hexString.toString();
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody User user) {
        log.info("New user signup request {}",user);
        if (userRepository.findByUsername(user.getUsername())==null){
            user.setPassword(getMD5Hash(user.getPassword()));
            user.setId(0);
            userRepository.save(user);
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Find the user in the database by username
        User existingUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists and the password is correct
        if (existingUser != null && getMD5Hash(user.getPassword()).equals(existingUser.getPassword())) {
            // Generate JWT token
            return jwtUtil.generateToken(existingUser.getUsername());
        } else {
            // Authentication failed
            return "Invalid credentials";
        }
    }
}
