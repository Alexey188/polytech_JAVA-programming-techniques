package com.example.polytech.adapters.web;

import com.example.polytech.domain.User;
import com.example.polytech.ports.UserRepository;
import com.example.polytech.ports.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    public UserController(UserRepository repo) { this.service = new UserService(repo); }

    public record RegisterRequest(@Email @NotBlank String email,
                                  @NotBlank String fullName) {}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody RegisterRequest body) {
        return service.register(body.email(), body.fullName());
    }

    @GetMapping("/login")
    public User login(@RequestParam("email") @Email String email) {
        return service.loginByEmail(email).orElseThrow(NotFound::new);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserService.DuplicateEmail.class)
    public void duplicateEmail() {}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class NotFound extends RuntimeException {}
}
