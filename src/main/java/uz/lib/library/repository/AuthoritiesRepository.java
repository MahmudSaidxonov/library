package uz.lib.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.lib.library.model.Authorities;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
//    @Query("select a from Authorities a where a.id in (select t.authorityId from Roles t where t.userId = :userId)")
    @Query(value = "select a.* from authorities a where a.id in (select t.authority_id from roles t where t.user_id = :userId)", nativeQuery = true)
    Optional<List<Authorities>> getAuthoritiesByUserId(@Param("userId") Integer userId);
}
