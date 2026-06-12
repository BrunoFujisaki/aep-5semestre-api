package obeservacao.api.repository;

import obeservacao.api.model.User;
import obeservacao.api.repository.projection.RoleCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            select new obeservacao.api.repository.projection.RoleCountProjection(u.role, count(u))
            from User u
            group by u.role
            """)
    List<RoleCountProjection> countGroupedByRole();
}
