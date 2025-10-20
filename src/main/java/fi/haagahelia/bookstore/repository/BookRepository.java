package fi.haagahelia.bookstore.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fi.haagahelia.bookstore.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByAuthor(@Param("author") String author);
    
    List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);

    List<Book> findByCategoryName(@Param("category") String category);
    

}
