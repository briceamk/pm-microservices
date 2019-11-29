package cm.gelodia.uaa.service;

import cm.gelodia.uaa.model.Role;
import org.springframework.data.domain.Page;

public interface RoleService {

    Role update(Role role);
    Role getById(String id);
    Page<Role> getAll(Integer page, Integer size);
    Role getByName(String name);
}
