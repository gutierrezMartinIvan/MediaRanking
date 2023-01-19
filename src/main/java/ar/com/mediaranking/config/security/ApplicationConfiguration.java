package ar.com.mediaranking.config.security;

import ar.com.mediaranking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findUserByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
