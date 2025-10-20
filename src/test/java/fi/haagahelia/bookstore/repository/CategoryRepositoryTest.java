package fi.haagahelia.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fi.haagahelia.bookstore.model.Category;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findByNameShouldReturnCategory() {
        Category category = new Category("Mystery");
        categoryRepository.save(category);
        
        Category found = categoryRepository.findByName("Mystery").get(0);
        assertThat(found.getName()).isEqualTo("Mystery");
    }

    @Test
    public void createNewCategory() {
        long initialCount = categoryRepository.count();
        
        Category category = new Category("Biography");
        Category saved = categoryRepository.save(category);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(categoryRepository.count()).isEqualTo(initialCount + 1);
    }

    @Test
    public void deleteCategory() {
        Category category = categoryRepository.save(new Category("History"));
        Long categoryId = category.getId();
        
        categoryRepository.deleteById(categoryId);
        
        assertThat(categoryRepository.findById(categoryId)).isEmpty();
    }
}