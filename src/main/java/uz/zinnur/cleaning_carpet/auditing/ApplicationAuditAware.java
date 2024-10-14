package uz.zinnur.cleaning_carpet.auditing;


import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.zinnur.cleaning_carpet.model.Employee;

import java.util.Optional;
import java.util.UUID;

//@Component("auditor") look ApplicationConfig
public class ApplicationAuditAware implements AuditorAware<UUID> {
    @Override
    public @NonNull Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        // Assuming your user object has UUID as an identifier
        Employee principal = (Employee) authentication.getPrincipal();
        return Optional.ofNullable(principal.getId());
    }
}