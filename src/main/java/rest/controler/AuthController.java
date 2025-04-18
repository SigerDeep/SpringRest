package rest.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.service.AuthControllerService;
import rest.filters.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthControllerService authControllerService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        return authControllerService.authenticate(loginRequest);
    }
}
