package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.bookstore.model.AppUser;
import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.Category;
import fi.haagahelia.bookstore.repository.BookRepository;
import fi.haagahelia.bookstore.repository.CategoryRepository;
import fi.haagahelia.bookstore.repository.UserRepository;

@SpringBootApplication
public class MaabsbookstoreApplication {

    private final BookRepository bookRepository;

    MaabsbookstoreApplication(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MaabsbookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository, 
    CategoryRepository categoryRepository, UserRepository userRepository) {
        return (args) -> {
            Category fiction = categoryRepository.save(new Category("Fiction"));
            Category programming = categoryRepository.save(new Category("Programming"));
            Category horror = categoryRepository.save(new Category("Horror"));

            bookRepository.save(new Book("Clean Code", "Robert C. Martin", 2008, "9780132350884", 29.99, programming));
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", 2018, "9780134685991", 39.99, programming));
            bookRepository.save(new Book("Spring in Action", "Craig Walls", 2018, "9781617294945", 34.99, programming));
            bookRepository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 10.99, fiction));
            bookRepository.save(new Book("1984", "George Orwell", 1949, "9780451524935", 9.99, fiction));
            bookRepository.save(new Book("The Shining", "Stephen King", 1977, "9780307743657", 14.99, horror));
            bookRepository.save(new Book("It", "Stephen King", 1986, "9781501142970", 18.99, horror));

BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

if (!userRepository.existsByUsername("user")) {
    AppUser user1 = new AppUser(
        "user", 
        bcrypt.encode("user"), 
        "user@bookstore.com", 
        "USER"
    );
    userRepository.save(user1);
}

if (!userRepository.existsByUsername("admin")) {
    AppUser user2 = new AppUser(
        "admin", 
        bcrypt.encode("admin"), 
        "admin@bookstore.com", 
        "ADMIN"
    );
    userRepository.save(user2);
}

System.out.println("Sample users created or already exist:");
    };
    }
}

