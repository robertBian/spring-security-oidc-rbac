package dev.robertbian.security;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import static org.assertj.core.api.Assertions.assertThat;

class RealmRolesConverterTest {
    private final RealmRolesConverter converter = new RealmRolesConverter();

    @Test void mapsRealmRolesAndPreservesScopes() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "none")
                .claim("sub", "test")
                .claim("scope", "read")
                .claim("realm_access", Map.of("roles", List.of("admin", "viewer")))
                .build();
        assertThat(converter.convert(jwt)).extracting("authority")
                .contains("ROLE_ADMIN", "ROLE_VIEWER", "SCOPE_read");
    }

    @Test void handlesMissingRealmRoles() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "none").claim("sub", "test").build();
        assertThat(converter.convert(jwt)).isEmpty();
    }
}
