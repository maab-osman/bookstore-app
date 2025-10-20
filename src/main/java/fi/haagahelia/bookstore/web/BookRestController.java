package fi.haagahelia.bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.bookstore.model.Book;
import fi.haagahelia.bookstore.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookRepository repository;

    @GetMapping("")
    public List<Book> bookListRest() {
        return (List<Book>) repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
        Optional<Book> book = repository.findById(bookId);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}