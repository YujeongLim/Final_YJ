package com.uni.mental.config;

import com.uni.mental.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final AuthenticationService authenticationService;
    @Autowired
    public SpringSecurityConfiguration(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer configure() {

        return (web) -> web.ignoring().requestMatchers(
                "/css/**", "/js/**", "/images/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        Map<String, List<String>> permitListMap = authenticationService.getPermitListMap();

        List<String> adminPermitList = permitListMap.get("adminPermitList");
        List<String> memberPermitList = permitListMap.get("memberPermitList");

        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/login/kakao-login"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(memberPermitList.toArray(new String[0])).hasAnyRole("MEMBER", "ADMIN")
                        .requestMatchers(adminPermitList.toArray(new String[0])).hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login/loginpage")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main", true)
                        .failureUrl("/login/loginpage?error=true")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login/loginpage")
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/error/denied"));

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        Map<String, List<String>> permitListMap = authenticationService.getPermitListMap();
//
//        List<String> adminPermitList = permitListMap.get("adminPermitList");
//        List<String> memberPermitList = permitListMap.get("memberPermitList");
//
//        http
//                // CSRF 설정 수정: 카카오 로그인 처리 URL 예외 처리
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/login/kakao-login") // CSRF 보호를 비활성화할 경로 지정
//                )
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(memberPermitList.toArray(new String[0])).hasAnyRole("MEMBER", "ADMIN")
//                        .requestMatchers(adminPermitList.toArray(new String[0])).hasRole("ADMIN")
//                        .anyRequest().permitAll()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login/loginpage")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/")
//                        .failureForwardUrl("/error/login")
//                )
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout/logoutpage"))
//                        .deleteCookies("JSESSIONID")
//                        .invalidateHttpSession(true)
//                        .logoutSuccessUrl("/")
//                )
//                .exceptionHandling(exception -> exception.accessDeniedPage("/error/denied"));
//
//        return http.build();
//    }


}
