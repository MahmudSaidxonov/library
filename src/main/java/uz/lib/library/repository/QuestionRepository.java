package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Question;
import uz.lib.library.model.Quiz;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
//    Set<Question> findByQuiz(Quiz quiz);

    Optional<Question> getByContent(String  content);
    @Query(value = "SELECT * FROM question where quiz_id = :quizId" ,nativeQuery = true)
    List<Question> findByQuiz(@Param("quizId") Integer quizId);

}
