package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.FileType;

import java.util.Optional;

@Repository
public interface FileResolutionRepository extends JpaRepository<FileType, Integer> {
    Optional<FileType> findByType(String type);

}
