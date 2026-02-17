package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Book;
import uz.lib.library.projections.BookProjection;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, BookCustomRepository {
    @Query(value = "SELECT b.id AS id," +
            "       b.name AS name," +
            "       b.description AS description," +
            "       b.category_id AS categoryId," +
            "       b.id_author AS authorId " +
            "       FROM book b " +
            "       limit :size offset :currentPage * :size", nativeQuery = true)
    List<BookProjection> getBooks(Integer currentPage, Integer size);
}
