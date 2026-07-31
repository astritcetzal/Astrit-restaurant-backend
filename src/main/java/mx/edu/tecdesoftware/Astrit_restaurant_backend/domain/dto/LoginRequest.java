package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.dto;

public class LoginRequest {
    private String username; // Evaluaremos este contra el "nombre" del cliente
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}