package fi.haagahelia.bookstore.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.model.Category;
import fi.haagahelia.bookstore.repository.BookRepository;
import fi.haagahelia.bookstore.repository.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
        categoryRepository.deleteAll();
        
        Category fiction = categoryRepository.save(new Category("Fiction"));
        
        // Create test books
        bookRepository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "123456789", 12.99, fiction));
        bookRepository.save(new Book("Test Book", "Test Author", 2024, "987654321", 15.99, fiction));
    }

    // Test CUSTOM REST controller endpoints
    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void getAllBooksShouldReturnJsonArray() throws Exception {
        this.mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].title", is("The Great Gatsby")));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void getBookByIdShouldReturnBook() throws Exception {
        // Get the first book's ID
        Book book = bookRepository.findAll().iterator().next();
        
        this.mockMvc.perform(get("/api/books/" + book.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

@Test
@WithMockUser(username = "testuser", roles = "USER")
public void getBookByInvalidIdShouldReturnNotFound() throws Exception {
    this.mockMvc.perform(get("/api/books/9999"))
        .andExpect(status().isNotFound());
}

}

    