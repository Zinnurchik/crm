package uz.zinnur.cleaning_carpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository userRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
