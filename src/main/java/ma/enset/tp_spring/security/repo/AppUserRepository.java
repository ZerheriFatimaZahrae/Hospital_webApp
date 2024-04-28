package ma.enset.tp_spring.security.repo;

import ma.enset.tp_spring.entites.Patient;
import ma.enset.tp_spring.security.entites.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {

    AppUser findByUsername(String username);
}
