package ma.enset.tp_spring.repository;

import ma.enset.tp_spring.entites.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository  extends JpaRepository<Patient,Long> {

    //chercher un patient  avec pageable : pour transferer num de page et size de page
    //containt : son equivalent c est like ds SQL
    Page<Patient> findByNomContains(String keyword,Pageable pageable);
}
