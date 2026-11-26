package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain;

public enum Role {
    ADMIN,
    CLIENTE;

    public static Role fromString(String value) {
        if (value == null || value.isBlank()) {
            return CLIENTE;
        }
        String normalized = value.trim().toUpperCase();
        if (normalized.startsWith("ROLE_")) {
            normalized = normalized.substring(5);
        }
        for (Role role : values()) {
            if (role.name().equals(normalized)) {
                return role;
            }
        }
        return CLIENTE;
    }
}
