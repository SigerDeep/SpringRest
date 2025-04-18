package rest.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rest.service.UserControllerService;



@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserControllerService userControllerService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userControllerService.getUser(userDetails);
    }
}
