package com.example.springsecurityhibernatejwt;

import com.example.springsecurityhibernatejwt.user.User;
import com.example.springsecurityhibernatejwt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Khi chương trình chạy
        // Insert vào csdl một user.
        User user = new User();
        user.setUsername("ducanh");
        user.setPassword(passwordEncoder.encode("ducanh"));
        userRepository.save(user);
        System.out.println(user);
    }
}
