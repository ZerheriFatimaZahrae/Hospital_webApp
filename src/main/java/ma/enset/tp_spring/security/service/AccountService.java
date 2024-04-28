package ma.enset.tp_spring.security.service;

import ma.enset.tp_spring.security.entites.AppRole;
import ma.enset.tp_spring.security.entites.AppUser;

public interface AccountService {

    AppUser addNewUser(String username, String password, String email ,String confirmPassword) ;
    AppRole addNewRole(String role);

    void AddRoleToUser(String username,String   role);
    void removeRoleFromUser(String username,String role);

    AppUser loadUserByUsername(String username);


}


