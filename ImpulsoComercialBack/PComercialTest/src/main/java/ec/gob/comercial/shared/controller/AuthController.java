package ec.gob.comercial.shared.controller;

import ec.gob.comercial.shared.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public AuthController(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/dev-token")
    public Map<String, String> devToken() {
        UserDetails user = userDetailsService.loadUserByUsername("admin@municipio.gob.ec");
        String token = jwtTokenProvider.generateToken(user);
        return Map.of("token", token);
    }
}
