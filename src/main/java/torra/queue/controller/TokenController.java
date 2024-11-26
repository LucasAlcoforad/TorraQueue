package torra.queue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import torra.queue.controller.dto.LoginRequest;
import torra.queue.controller.dto.LoginResponse;
import torra.queue.service.TokenService;

@RestController
public class TokenController {
    public TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(tokenService.login(loginRequest));
    }
}
