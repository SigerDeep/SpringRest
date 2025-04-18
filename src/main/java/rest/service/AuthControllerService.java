package rest.service;

import org.springframework.http.ResponseEntity;
import rest.filters.LoginRequest;

public interface AuthControllerService {
    ResponseEntity<?> authenticate(LoginRequest loginRequest);
}
