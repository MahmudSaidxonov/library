package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "WITH ins AS (INSERT INTO author(id, name, created_at) VALUES (nextval('author_id_seq') , ?1, current_timestamp) ON CONFLICT (name) DO NOTHING RETURNING *) SELECT * FROM ins UNION ALL SELECT * FROM author WHERE name = ?1", nativeQuery = true)
    Optional<Author> addIfNewAuthor(String name);
//        @Query(value = "INSERT INTO author(name) VALUES (?1) ON CONFLICT (name) DO NOTHING RETURNING *", nativeQuery = true)
//    Optional<Author> addIfNewAuthor(String name);
    @Query(value = "SELECT b FROM Author b")
    List<Author> findAllAuthors();
}
