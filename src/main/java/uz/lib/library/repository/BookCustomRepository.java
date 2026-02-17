package uz.lib.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Book;

import java.util.List;


public interface BookCustomRepository {
    Page<Book> universalSearch(String query, List<String> filter, String sorting, String ordering, Integer size, Integer currentPage);
    Page<Book> getWithSort(Integer id, List<String> filter,String sorting, String ordering, Integer currentPage);
    boolean insertViewedProduct(Integer userId, Integer productId);
}
