package ma.enset.tp_spring;

import ma.enset.tp_spring.entites.Patient;
import ma.enset.tp_spring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TpSpringApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(TpSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"Racha",new Date(2003,01,11),false,10));
        patientRepository.save(new Patient(null,"Fatima zahrae",new Date(2003,01,11),true,20));
        patientRepository.save(new Patient(null,"Souad",new Date(),true,10));

        List<Patient> patients = patientRepository.findAll();
        patients.forEach(p->{
            System.out.println(p.toString());
        });
        // par utilisation de Builder , annotations
        Patient patient=Patient.builder()
                .nom("fati")
                .dateNaissance(new Date())
                .malade(false)
                .score(100)
                .build();

    }
}
