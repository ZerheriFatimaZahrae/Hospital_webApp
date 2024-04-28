package ma.enset.tp_spring.security.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.enset.tp_spring.security.entites.AppRole;
import ma.enset.tp_spring.security.entites.AppUser;
import ma.enset.tp_spring.security.repo.AppRoleRepository;
import ma.enset.tp_spring.security.repo.AppUserRepository;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @Transactional @AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser user=appUserRepository.findByUsername(username);
        if(user!=null) {
            throw new RuntimeException("User already exists");
        }
        if(!password.equals(confirmPassword)){
            throw new RuntimeException("Passwords do not match");
        }
        AppUser user1 =AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        AppUser saveUser=appUserRepository.save(user1);

        return saveUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole=appRoleRepository.findByRole(role);
        if(appRole!=null){
            throw new RuntimeException("Role already exists");
        }
        AppRole appRole1=AppRole.builder()
                .role(role)
                .build();
            AppRole saveRole=appRoleRepository.save(appRole1);
        return saveRole;
    }

    @Override
    public void AddRoleToUser(String username, String role) {
        AppRole appRole=appRoleRepository.findById(role).get();
        AppUser appUser=appUserRepository.findByUsername(username);

        if(appRole==null){
            throw new RuntimeException("Role don't exists");
        }
        if(appUser==null){
            throw new RuntimeException("User don't exists");
        }
        appUser.getRoles().add(appRole);


    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppRole appRole=appRoleRepository.findById(role).get();
        AppUser appUser=appUserRepository.findByUsername(username);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
