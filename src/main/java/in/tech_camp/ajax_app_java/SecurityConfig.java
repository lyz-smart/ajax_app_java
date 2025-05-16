package in.tech_camp.ajax_app_java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig { 
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorizeRequests -> authorizeRequests
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .permitAll() 
      )
      .httpBasic(httpBasicCustomizer -> { });

    return http.build();
  }

  // 建立一个 具有 admin 权限的 user
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    // 读取环境变量为变量赋值
    String username = System.getenv("BASIC_AUTH_USER");
    String password = System.getenv("BASIC_AUTH_PASSWORD");

    UserDetails user = User.withUsername(username)
        .password(encoder.encode(password))
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}