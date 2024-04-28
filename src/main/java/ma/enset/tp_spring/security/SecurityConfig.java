package ma.enset.tp_spring.security;

import lombok.AllArgsConstructor;
import ma.enset.tp_spring.security.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration @EnableWebSecurity @AllArgsConstructor
//securiser les methods
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImp userDetailsServiceImp;

    //@Bean // au lieu de chercher les utilisateurs par la fct in memory
    // on utilise jdbcUserDetailsManager au quel il va chercher ds datasource les utilisateur
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        //encoder le password
        String encodedPassword = passwordEncoder.encode("1234");
        System.out.println(encodedPassword);
        //determener les prs ayant l'acces a l'app
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                User.withUsername("admin").password(encodedPassword).roles("USER","ADMIN").build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();//apres l authentification est reussi redireger l utilisateur vers /
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();
        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/delete").hasRole("ADMIN");


        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        httpSecurity.userDetailsService(userDetailsServiceImp);
        //une fois l utilisateur saisi son username et son password fait appel a fct userDetailsServiceImp
        return httpSecurity.build();
    }
}
