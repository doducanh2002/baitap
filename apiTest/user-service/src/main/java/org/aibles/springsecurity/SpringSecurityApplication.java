
import org.aibles.springsecurity.user.User;
import org.aibles.springsecurity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {
        public static void main(String[] args) {
            SpringApplication.run(SpringSecurityApplication.class, args);
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
