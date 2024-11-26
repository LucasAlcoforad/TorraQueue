package torra.queue.service;

import org.apache.juli.logging.Log;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import torra.queue.controller.dto.LoginRequest;
import torra.queue.controller.dto.LoginResponse;
import torra.queue.entity.Admin;
import torra.queue.repository.AdminRepository;
import torra.queue.repository.ClientRepository;

import java.time.Instant;

@Service
public class TokenService {
    private final AdminRepository adminRepository;

    private ClientRepository clientRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtEncoder jwtEncoder;

    public TokenService(AdminRepository adminRepository,
                        ClientRepository clientRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        JwtEncoder jwtEncoder) {

        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest){
        var admin = adminRepository.findByEmail(loginRequest.email());
        if (admin.isPresent()){
            if (!admin.get().isLoginCorrect(loginRequest.password(), bCryptPasswordEncoder)){
                throw new BadCredentialsException("password is invalid!");
            }
            return this.generateToken(admin.get().getId().toString(), true);
        }
        var client = clientRepository.findByEmail(loginRequest.email());
        if (client.isPresent()){
            return this.generateToken(client.get().getId().toString(), false);
        }
        throw new BadCredentialsException("user is invalid!");
    }

    public LoginResponse generateToken(String id, boolean isAdmin){
        var now = Instant.now();
        var expiresIn = 300L;
        var claims = JwtClaimsSet.builder()
                .issuer("torraflow")
                .subject(id)
                .claim("isAdmin",isAdmin)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue,expiresIn);
    }

}
