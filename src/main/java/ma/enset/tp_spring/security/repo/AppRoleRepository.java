package ma.enset.tp_spring.security.repo;

import ma.enset.tp_spring.entites.Patient;
import ma.enset.tp_spring.security.entites.AppRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
    AppRole findByRole(String role);

}
