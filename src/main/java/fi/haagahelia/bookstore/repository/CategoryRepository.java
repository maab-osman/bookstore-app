package fi.haagahelia.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.bookstore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    java.util.List<Category> findByName(String name);

}
