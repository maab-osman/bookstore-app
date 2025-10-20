package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fi.haagahelia.bookstore.model.AppUser;
import fi.haagahelia.bookstore.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("user")) {
            AppUser defaultUser = new AppUser();
            defaultUser.setUsername("user");
            defaultUser.setPassword("{noop}password");
            defaultUser.setRole("USER"); 
            userRepository.save(defaultUser);
            System.out.println("Default user created.");
        } else {
            System.out.println("Default user already exists.");
        }
    }
}

