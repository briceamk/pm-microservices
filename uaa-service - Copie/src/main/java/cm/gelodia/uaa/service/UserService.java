package cm.gelodia.uaa.service;

import cm.gelodia.uaa.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User add(User user);
    User update(User user);
    User getById(String id);
    Page<User> getAll(Integer page, Integer size);
    void delete(String id);
    void deleteAll(List<String> ids);
}
