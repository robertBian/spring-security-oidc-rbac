package dev.robertbian.security;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccessController {
    @GetMapping("/public/ping")
    public Map<String, String> ping() { return Map.of("status", "ok"); }

    @GetMapping("/profile/me")
    public Map<String, Object> profile(Authentication authentication) {
        return Map.of("subject", authentication.getName(), "authorities",
                authentication.getAuthorities().stream().map(a -> a.getAuthority()).sorted().toList());
    }

    @GetMapping("/admin/report")
    public Map<String, Object> report() {
        return Map.of("classification", "demo-only", "activeAccounts", 42);
    }
}
