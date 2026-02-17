package uz.lib.library.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import uz.lib.library.model.Book;
import uz.lib.library.model.Category;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookRepositoryImpl implements BookCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Book> universalSearch(String query, List<String> filter,String sorting, String ordering, Integer size, Integer currentPage) {
        size = Math.max(size, 0);
        int page = Math.max(currentPage,0);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);

        Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + query.toLowerCase() + "%");
        Predicate categoryPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), "%" + query.toLowerCase() + "%");
        Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + query.toLowerCase() + "%");
        Predicate finalPredicate = criteriaBuilder.or(namePredicate, categoryPredicate, descriptionPredicate);



        TypedQuery<Category> customQuery = entityManager.createQuery("select c from Category c where LOWER( c.name ) LIKE :query", Category.class);
        customQuery.setParameter("query","%"+query.toLowerCase()+"%");

        if(filter != null && !filter.isEmpty()){
            Predicate filterPredicate = root.get("author").get("name").in(filter);
            finalPredicate = criteriaBuilder.and(filterPredicate,filterPredicate);
            criteriaQuery.where(finalPredicate);
        }else {
            criteriaQuery.where(finalPredicate);
        }
        if (sorting != null && ordering != null) {
            Path<Object> sortPath;
            if (ordering.equalsIgnoreCase("ascending")) {
                sortPath = root.get(sorting);
                criteriaQuery.orderBy(criteriaBuilder.asc(sortPath));
            } else if (ordering.equalsIgnoreCase("descending")) {
                sortPath = root.get(sorting);
                criteriaQuery.orderBy(criteriaBuilder.desc(sortPath));
            }
        }

        TypedQuery<Book> search = entityManager.createQuery(criteriaQuery);

        long count = search.getResultList().size();


        if (count > 0 && count / size <= page){
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        search.setFirstResult(size * page);
        search.setMaxResults(size);

        if(search.getResultList().isEmpty()){
            if(!customQuery.getResultList().isEmpty()){
                return getWithSort(customQuery.getResultList().get(0).getId(),filter,sorting,ordering,currentPage);
            }
        }


        return new PageImpl<>(search.getResultList(), PageRequest.of(page,size),count);

    }

    @Override
    public Page<Book> getWithSort(Integer id, List<String> filter, String sorting, String ordering, Integer currentPage) {
        int size = 10;
        int page = Math.max(currentPage,0);

        List<Integer> categoryIds = getCategoriesWithChildren(id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);

        Predicate categoryPredicate = root.get("category").get("id").in(categoryIds);
        if(filter != null && !filter.isEmpty()){
            Predicate bookPredicate = root.get("book").get("name").in(filter);
            criteriaQuery.select(root).where(criteriaBuilder.and(categoryPredicate,bookPredicate));
        }else {
            criteriaQuery.select(root).where(categoryPredicate);
        }


        if(sorting != null && ordering != null){
            Path<Object> sortPath = root.get(sorting);
            Order order = ordering.equalsIgnoreCase("ascending") ? criteriaBuilder.asc(sortPath) : criteriaBuilder.desc(sortPath);
            criteriaQuery.orderBy(order);
        }

        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);

        long count = query.getResultList().size();
        if (count > 0 && count / size <= page) {
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return new PageImpl<>(query.getResultList(), PageRequest.of(page, size), count);
    }

    private List<Integer> getCategoriesWithChildren(Integer categoryId) {

        List<Integer> categoryIds = entityManager.createNativeQuery(
                        "WITH RECURSIVE child_categories AS (" +
                                "  SELECT id " +
                                "  FROM category " +
                                "  WHERE id = :categoryId " +
                                "  UNION ALL " +
                                "  SELECT c.id " +
                                "  FROM category c " +
                                "  JOIN child_categories cc ON cc.id = c.parent_id" +
                                ") " +
                                "SELECT id " +
                                "FROM child_categories"
                )
                .setParameter("categoryId", categoryId)
                .getResultList();

        return categoryIds;
    }

    @Override
    public boolean insertViewedProduct(Integer userId, Integer productId) {
        return false;
    }
}
