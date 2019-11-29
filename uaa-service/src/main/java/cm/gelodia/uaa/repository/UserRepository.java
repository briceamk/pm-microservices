package cm.gelodia.uaa.repository;

import cm.gelodia.uaa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User>  getByUsernameOrEmail(String  username, String email);
    Boolean existsByUsernameOrEmail(String username, String email);
    Optional<User>  getByUsernameOrEmailAndCompanyId(String  username, String email, String companyId);
}
