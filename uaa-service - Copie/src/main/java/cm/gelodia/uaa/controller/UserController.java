package cm.gelodia.uaa.controller;

import cm.gelodia.uaa.constant.ConstantTypes;
import cm.gelodia.uaa.model.User;
import cm.gelodia.uaa.payload.ApiResponse;
import cm.gelodia.uaa.service.UserService;
import cm.gelodia.uaa.service.ValidationErrorService;
import io.swagger.models.Response;
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
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {

    private UserService userService;
    private ValidationErrorService validationErrorService;

    public UserController(UserService userService, ValidationErrorService validationErrorService) {
        this.userService = userService;
        this.validationErrorService = validationErrorService;
    }


    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        user = userService.add(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/uaa/users/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new ApiResponse(true, "user saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        userService.update(user);
        return ResponseEntity.ok().body(new ApiResponse(true, "user updated successfully!"));
    }

    @GetMapping
    public @ResponseBody Page<User> getUserByPage(
            @RequestParam(name = "page", required = true, defaultValue = ConstantTypes.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = true, defaultValue = ConstantTypes.DEFAULT_PAGE_SIZE) Integer size) {
        return userService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "user deleted successfully!"));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<?> deleteUser(@PathVariable List<String> ids) {
        userService.deleteAll(ids);
        return ResponseEntity.ok().body(new ApiResponse(true, "users deleted successfully!"));
    }


}
