package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Quiz;
import uz.lib.library.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
//    List<Quiz> findByIsActive(boolean b);
    Optional<Quiz> findByTitle(String title);
    Optional<Quiz> findAllByIsActive(Boolean b);

    Optional<Quiz> findByIdAndIsActive(Integer id, Boolean isActive);


}
