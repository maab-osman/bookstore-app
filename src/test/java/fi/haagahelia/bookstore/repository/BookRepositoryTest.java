package fi.haagahelia.bookstore.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.Category;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category fiction;
    private Category scifi;
    private Book book1, book2, book3;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        categoryRepository.deleteAll();
        
        fiction = categoryRepository.save(new Category("Fiction"));
        scifi = categoryRepository.save(new Category("Science Fiction"));
        
        book1 = bookRepository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "123456789", 12.99, fiction));
        book2 = bookRepository.save(new Book("Dune", "Frank Herbert", 1965, "987654321", 15.99, scifi));
        book3 = bookRepository.save(new Book("1984", "George Orwell", 1949, "555555555", 10.99, fiction));
    }

    @Test
    public void findByTitleContainingShouldReturnBooks() {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase("great");
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("F. Scott Fitzgerald");
    }

    @Test
    public void findByAuthorShouldReturnBooks() {
        List<Book> books = bookRepository.findByAuthor("George Orwell");
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("1984");
    }

    @Test
    public void createNewBook() {
        long initialCount = bookRepository.count();
        
        Book newBook = new Book("New Book", "New Author", 2024, "999999999", 20.99, scifi);
        Book savedBook = bookRepository.save(newBook);
        
        assertThat(savedBook.getId()).isNotNull();
        assertThat(bookRepository.count()).isEqualTo(initialCount + 1);
    }

    @Test
    public void deleteBook() {
        long initialCount = bookRepository.count();
        
        bookRepository.delete(book1);
        
        assertThat(bookRepository.count()).isEqualTo(initialCount - 1);
        assertThat(bookRepository.findById(book1.getId())).isEmpty();
    }

    @Test
    public void findByCategoryNameShouldReturnBooks() {
        List<Book> books = bookRepository.findByCategoryName("Fiction");
        assertThat(books).hasSize(2); // The Great Gatsby and 1984
    }

    @Test
    public void findAllShouldReturnAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        assertThat(books).hasSize(3);
    }
}