package fi.haagahelia.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.bookstore.model.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    boolean existsByUsername(String username);

}