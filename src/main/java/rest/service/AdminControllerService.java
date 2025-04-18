package rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import rest.model.User;


import java.util.List;

public interface AdminControllerService {

    List<User> getAllUsers();

    User getUser(int id);

    ResponseEntity<?> update(User user, BindingResult bindingResult, int id);

    ResponseEntity<?> delete(int id);

    ResponseEntity<?> create(User user, BindingResult bindingResult);
}
