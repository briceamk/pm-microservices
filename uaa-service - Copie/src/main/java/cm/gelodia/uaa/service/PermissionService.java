package cm.gelodia.uaa.service;

import cm.gelodia.uaa.model.Permission;

public interface PermissionService {

    Permission getByName(String name);
}
