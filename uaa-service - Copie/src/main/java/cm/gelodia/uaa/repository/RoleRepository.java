package cm.gelodia.uaa.repository;

import cm.gelodia.uaa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> getByName(String name);
}
