package ar.com.mediaranking.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/reviews/series").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/reviews/movie").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/reviews/user").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/reviews/series").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/reviews/movie").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/reviews/*").authenticated()

                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/movies").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/movies/*").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/movies").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/movies").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/movies/list").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/movies/*").authenticated()

                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/series").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/series/*").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/series/*").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/series").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/series/*").authenticated()

                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/episode").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/episode/*").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/episode/*").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/episode").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/episode/save/list").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/episode/*").authenticated()

                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/season").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/season/*").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/season/*").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/season").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/season/list").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.PATCH, "/season/*").authenticated()

                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/user").permitAll()
                //.and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/user/*/admin").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/user").authenticated()
                .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/user/*").authenticated()

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
