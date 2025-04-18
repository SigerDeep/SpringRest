package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;
import rest.model.Role;
import rest.model.User;
import rest.repositories.RoleRepository;
import rest.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminControllerServiceImpl implements AdminControllerService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public ResponseEntity<?> update(User user, BindingResult bindingResult, int id) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors); }

        User userForUpdate = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Set<Role> attachedRoles = user.getRoles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found")))
                .collect(Collectors.toSet());

        userForUpdate.update(user, attachedRoles);
        userRepository.save(userForUpdate);

        return ResponseEntity.ok(Map.of("message", "User updated"));
    }

    public ResponseEntity<?> delete(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
        return ResponseEntity.ok(Map.of("message", "User deleted", "id", id));
    }

    public ResponseEntity<?> create(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors); }

        Set<Role> attachedRoles = user.getRoles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found")))
                .collect(Collectors.toSet());

        user.setRoles(attachedRoles);
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User created", "id", user.getId()));
    }
}
