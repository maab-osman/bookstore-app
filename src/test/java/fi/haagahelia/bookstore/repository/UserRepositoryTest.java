package fi.haagahelia.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.bookstore.model.AppUser;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameShouldReturnUser() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        AppUser user = new AppUser("testuser", bcrypt.encode("password"), "test@bookstore.com", "USER");
        userRepository.save(user);
        
        AppUser found = userRepository.findByUsername("testuser");
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("test@bookstore.com");
        assertThat(found.getRole()).isEqualTo("USER");
    }

    @Test
    public void createNewUser() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        AppUser user = new AppUser("newuser", bcrypt.encode("newpass"), "new@bookstore.com", "ADMIN");
        
        AppUser saved = userRepository.save(user);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("newuser");
        assertThat(saved.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void deleteUser() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        AppUser user = new AppUser("deleteuser", bcrypt.encode("deletepass"), "delete@bookstore.com", "USER");
        AppUser saved = userRepository.save(user);
        
        userRepository.deleteById(saved.getId());
        
        assertThat(userRepository.findByUsername("deleteuser")).isNull();
    }
}