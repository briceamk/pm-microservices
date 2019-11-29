package cm.gelodia.uaa.service.impl;

import cm.gelodia.uaa.exception.ResourceNotFoundException;
import cm.gelodia.uaa.model.Permission;
import cm.gelodia.uaa.model.Role;
import cm.gelodia.uaa.repository.RoleRepository;
import cm.gelodia.uaa.service.PermissionService;
import cm.gelodia.uaa.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private PermissionService permissionService;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getById(String id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role with Id '" + id + "' not found")
        );
    }

    @Override
    public Page<Role> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Role '" + name + "' not found")
        );
    }
}
