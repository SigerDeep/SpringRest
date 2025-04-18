package rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserControllerService {
    ResponseEntity<?> getUser(UserDetails userDetails);
}
