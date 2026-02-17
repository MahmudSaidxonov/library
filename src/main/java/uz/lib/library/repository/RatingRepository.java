package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Rating;
import uz.lib.library.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query(value = "SELECT * FROM rating " +
            "where (certificates_ball + rating.diploma_ball + rating.text_ball) > 0 " +
            "order by (certificates_ball + rating.diploma_ball + rating.text_ball) desc", nativeQuery = true)
    List<Rating> getRating();

    Optional<Rating> findByUserId(User user);

}
