package bg.oakhotelmanager.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequest->
                        authorizeRequest
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                //TODO
                                .requestMatchers("/","/login", "/register").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        //TODO
                        formLogin -> formLogin.loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/home",true)
                                .failureForwardUrl("/")
                )
                .logout(
                        //TODO
                        logout -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
                .build();
    }
}
