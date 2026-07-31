package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class AuthService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    public String login(String username, String password) {
        // Buscamos al cliente por su nombre de usuario
        Optional<Client> client = clientRepository.getClientByName(username);
        // Validamos que exista y que el password plano coincida con el hash
        if (client.isPresent() && passwordEncoder.matches(password, client.get().getContrasena())) {
            // Retornamos el token generado
            return jwtUtil.generateToken(client.get().getName());
        }
        // Si las credenciales no coinciden, retornamos nulo
        return null;
    }
}