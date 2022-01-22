package com.example.springsecurity;

import com.example.springsecurity.user.User;
import com.example.springsecurity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringsecurityApplication {
        public static void main(String[] args) {
            SpringApplication.run(SpringsecurityApplication.class, args);
        }

        @Autowired
        UserRepository userRepository;
        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
            // Khi chương trình chạy
            // Insert vào csdl một user.
            User user = new User();
            user.setUsername("loda");
            user.setPassword(passwordEncoder.encode("loda"));
            userRepository.save(user);
            System.out.println(user);
        }
}
