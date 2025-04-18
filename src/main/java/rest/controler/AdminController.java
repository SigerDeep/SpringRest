package rest.controler;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rest.model.User;
import rest.service.AdminControllerService;

import java.util.List;


@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private AdminControllerService adminControllerService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return adminControllerService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable int id) {
        return adminControllerService.getUser(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid User user, BindingResult bindingResult, @PathVariable int id) {
        return adminControllerService.update(user, bindingResult, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return adminControllerService.delete(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid User user, BindingResult bindingResult) {
        return adminControllerService.create(user, bindingResult);
    }
}
