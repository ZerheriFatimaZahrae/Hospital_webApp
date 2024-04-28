package ma.enset.tp_spring.security.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;

    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)//ds le chargement de AppUser charger aussi roles
    private List<AppRole> roles;
}
