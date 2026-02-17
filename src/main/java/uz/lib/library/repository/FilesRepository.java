package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Files;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer> {
    Optional<Files> findByUserId(Integer userId);
    Optional<List<Files>> findAllByUserId(Integer userId);


}
