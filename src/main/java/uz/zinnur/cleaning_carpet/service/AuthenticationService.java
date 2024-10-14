package uz.zinnur.cleaning_carpet.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.config.JwtService;
import uz.zinnur.cleaning_carpet.model.dao.AuthenticationResponse;
import uz.zinnur.cleaning_carpet.model.dto.AuthenticationDto;

@Service
public class AuthenticationService {
    private final EmployeeRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(EmployeeRepository repository,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService){
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(@NonNull AuthenticationDto authenticationDto) {
        try {
            // Try to authenticate the user using their username and password
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword())
            );

            // If the authentication was successful
            if (authenticate.isAuthenticated()) {
                // Find the employee in the repository by username
                var employee = repository.findByUsername(authenticationDto.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found in the repository"));

                // Generate a JWT token for the authenticated employee
                var jwtToken = jwtService.generateToken(employee);

                // Return the authentication response with the token
                return new AuthenticationResponse(jwtToken);
            }
        } catch (AuthenticationException ex) {
            // This catches any exception during authentication, including bad credentials
            throw new UsernameNotFoundException("Invalid username or password", ex);
        }

        // Fallback in case authentication fails (though it shouldn't reach here due to the exception)
        throw new UsernameNotFoundException("Failed to authenticate user");
    }
}