package cm.gelodia.uaa.service.impl;

import cm.gelodia.uaa.exception.ResourceNotFoundException;
import cm.gelodia.uaa.model.Permission;
import cm.gelodia.uaa.repository.PermissionRepository;
import cm.gelodia.uaa.service.PermissionService;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
    @Override
    public Permission getByName(String name) {
        return permissionRepository.getByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Permission with name '" + name + "' not found")
        );
    }
}
