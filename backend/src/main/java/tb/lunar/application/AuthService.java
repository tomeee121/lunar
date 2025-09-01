package tb.lunar.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tb.lunar.infrastructure.jpa.entity.AppUser;
import tb.lunar.infrastructure.jpa.repo.AppUserRepository;
import tb.lunar.infrastructure.security.JwtService;

@Service
public class AuthService {
    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager auth;
    private final JwtService jwt;

    public AuthService(AppUserRepository users, PasswordEncoder encoder,
                       AuthenticationManager auth, JwtService jwt) {
        this.users = users; this.encoder = encoder; this.auth = auth; this.jwt = jwt;
    }

    public String register(String email, String rawPassword) {
        if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already exists");
        var u = new AppUser();
        u.setEmail(email);
        u.setPassword(encoder.encode(rawPassword));
        u.setRole("USER");
        users.save(u);
        return jwt.generate(email);
    }

    public String login(String email, String rawPassword) {
        auth.authenticate(new UsernamePasswordAuthenticationToken(email, rawPassword));
        return jwt.generate(email);
    }
}