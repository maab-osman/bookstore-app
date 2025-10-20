package fi.haagahelia.bookstore.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void bookListShouldRequireAuthentication() throws Exception {
        this.mockMvc.perform(get("/booklist"))
            .andExpect(status().is3xxRedirection()); // Redirect to login
    }

    @Test
    public void bookListWithUserRoleShouldReturnView() throws Exception {
        this.mockMvc.perform(get("/booklist")
                .with(user("user").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(view().name("booklist"))
            .andExpect(model().attributeExists("books"));
    }

    @Test
    public void bookListWithAdminRoleShouldReturnView() throws Exception {
        this.mockMvc.perform(get("/booklist")
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(view().name("booklist"))
            .andExpect(model().attributeExists("books"));
    }

    @Test
    public void addBookPageShouldRequireAuthentication() throws Exception {
        this.mockMvc.perform(get("/addbook"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void addBookPageWithUserRoleShouldReturnView() throws Exception {
        this.mockMvc.perform(get("/addbook")
                .with(user("user").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(view().name("addbook"))
            .andExpect(model().attributeExists("book"))
            .andExpect(model().attributeExists("categories"));
    }
}