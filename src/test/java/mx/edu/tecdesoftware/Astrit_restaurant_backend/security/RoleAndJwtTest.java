package mx.edu.tecdesoftware.Astrit_restaurant_backend.security;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Role;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleAndJwtTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void jwtUtilShouldEmbedAndExtractRoleClaim() {
        JwtUtil jwtUtil = new JwtUtil();
        String adminToken = jwtUtil.generateToken("admin", Role.ADMIN.name());
        String clientToken = jwtUtil.generateToken("juan", Role.CLIENTE.name());

        assertEquals("admin", jwtUtil.extractUsername(adminToken));
        assertEquals("ADMIN", jwtUtil.extractRole(adminToken));
        assertTrue(jwtUtil.validateToken(adminToken, "admin"));

        assertEquals("juan", jwtUtil.extractUsername(clientToken));
        assertEquals("CLIENTE", jwtUtil.extractRole(clientToken));
    }

    @Test
    void clientServiceShouldAssignClienteRoleByDefaultOnRegistration() {
        Client newClient = new Client();
        newClient.setName("Maria");

        when(clientRepository.getClientByName("Maria")).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client saved = clientService.save(newClient);

        assertNotNull(saved);
        assertEquals(Role.CLIENTE, saved.getRole());
    }
}
