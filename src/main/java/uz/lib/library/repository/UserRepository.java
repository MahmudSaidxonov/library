package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findFirstByPhoneNumber(String phoneNumber);
    Optional<User> findByIdAndIsActive(Integer id, Boolean isActive);
    Optional<User> findFirstByPhoneNumberAndIsActive(String phoneNumber, Boolean isActive);
    Optional<User> findAllByIsActive(Boolean b);


}
