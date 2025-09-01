package tb.lunar.web.dto;

public class AuthDTOs {
    public record RegisterRequest(String email, String password) {}
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String token) {}
}
