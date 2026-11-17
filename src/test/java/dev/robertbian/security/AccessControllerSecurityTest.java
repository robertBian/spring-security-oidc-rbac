package dev.robertbian.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccessController.class)
@Import(SecurityConfig.class)
class AccessControllerSecurityTest {
    @Autowired MockMvc mvc;
    @MockitoBean JwtDecoder decoder;

    @Test void anonymousCanPingButCannotReadProfile() throws Exception {
        mvc.perform(get("/api/public/ping")).andExpect(status().isOk());
        mvc.perform(get("/api/profile/me")).andExpect(status().isUnauthorized());
    }

    @Test void nonAdminCannotReadAdminReport() throws Exception {
        mvc.perform(get("/api/admin/report").with(jwt().authorities(
                new SimpleGrantedAuthority("ROLE_VIEWER")))).andExpect(status().isForbidden());
    }

    @Test void adminCanReadAdminReport() throws Exception {
        mvc.perform(get("/api/admin/report").with(jwt().authorities(
                new SimpleGrantedAuthority("ROLE_ADMIN")))).andExpect(status().isOk());
    }
}
