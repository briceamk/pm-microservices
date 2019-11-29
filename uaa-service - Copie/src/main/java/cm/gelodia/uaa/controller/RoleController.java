package cm.gelodia.uaa.controller;

import cm.gelodia.uaa.constant.ConstantTypes;
import cm.gelodia.uaa.model.Role;
import cm.gelodia.uaa.model.User;
import cm.gelodia.uaa.payload.ApiResponse;
import cm.gelodia.uaa.service.RoleService;
import cm.gelodia.uaa.service.UserService;
import cm.gelodia.uaa.service.ValidationErrorService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/roles")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class RoleController {

    private RoleService roleService;
    private ValidationErrorService validationErrorService;

    public RoleController(RoleService roleService, ValidationErrorService validationErrorService) {
        this.roleService = roleService;
        this.validationErrorService = validationErrorService;
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@Valid @RequestBody Role role, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        roleService.update(role);
        return ResponseEntity.ok().body(new ApiResponse(true, "role updated successfully!"));
    }

    @GetMapping
    public @ResponseBody Page<Role> getRoleByPage(
            @RequestParam(name = "page", required = true, defaultValue = ConstantTypes.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = true, defaultValue = ConstantTypes.DEFAULT_PAGE_SIZE) Integer size) {
        return roleService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getById(id));
    }


}
