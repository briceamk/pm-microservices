package cm.gelodia.uaa.repository;

import cm.gelodia.uaa.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> getByName(String name);
}
