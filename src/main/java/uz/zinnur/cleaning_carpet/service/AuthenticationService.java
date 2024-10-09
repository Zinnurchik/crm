package uz.zinnur.cleaning_carpet.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    public AuthenticationResponse authenticate(AuthenticationDto authenticationDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));
        if (authenticate.isAuthenticated()) {
            var employee = repository.findByUsername(authenticationDto.getUsername()).orElseThrow(()->new UsernameNotFoundException("user not found"));
            var jwtToken = jwtService.generateToken(employee);
            System.out.println(jwtToken);
            return new AuthenticationResponse(jwtToken);
        }
        throw new UsernameNotFoundException("failed to authenticate user");
    }
}