package ma.enset.tp_spring.security.service;

import lombok.AllArgsConstructor;
import ma.enset.tp_spring.security.entites.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private AccountServiceImpl accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= accountService.loadUserByUsername(username);
        String[]  roles=appUser.getRoles().stream().map(u->u.getRole()).toArray(String[]::new);
        if (appUser == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

        UserDetails userDetails = User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles).build();

        return userDetails;
    }
}
