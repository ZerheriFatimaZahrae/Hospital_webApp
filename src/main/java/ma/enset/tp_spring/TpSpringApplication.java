package ma.enset.tp_spring;

import ma.enset.tp_spring.entites.Patient;
import ma.enset.tp_spring.repository.PatientRepository;
import ma.enset.tp_spring.security.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

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
        /*patientRepository.save(new Patient(null,"Racha",new Date(2003,01,11),false,10));
        patientRepository.save(new Patient(null,"Fatima zahrae",new Date(2003,01,11),true,20));
        patientRepository.save(new Patient(null,"Souad",new Date(),true,10));

        List<Patient> patients = patientRepository.findAll();
        patients.forEach(p->{
            System.out.println(p.toString());
        });*/
        // par utilisation de Builder , annotations
        /*Patient patient=Patient.builder()
                .nom("fati")
                .dateNaissance(new Date())
                .malade(false)
                .score(100)
                .build();*/

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        //Creer des utilisateurs et l'eregistrer ds DB
        return args -> {

            try {
                UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("fatima");
                // Si l'utilisateur existe déjà, aucun besoin de le créer
            } catch (UsernameNotFoundException ex) {
                // L'utilisateur n'existe pas, nous le créons
                jdbcUserDetailsManager.createUser(
                        User.withUsername("fatima").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
                );
            }



            try {
                UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user1");
                // Si l'utilisateur existe déjà, aucun besoin de le créer
            } catch (UsernameNotFoundException ex) {
                // L'utilisateur n'existe pas, nous le créons
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build()
                );

            }

            try {
                UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin");
            } catch (UsernameNotFoundException ex) {
                // L'utilisateur n'existe pas, nous le créons
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("ADMIN").build()
                );

            }


        };
    }
       // @Bean
         CommandLineRunner commandLineRunnerUserDetails(AccountServiceImpl accountService){
             return args -> {
                 accountService.addNewRole("USER");
                 accountService.addNewRole("ADMIN");

                 accountService.addNewUser("user11","1234","user11@gmail.com","1234");
                 accountService.addNewUser("user22","1234","user22@gmail.com","1234");
                 accountService.addNewUser("admin","1234","admin@gmail.com","1234");

                 accountService.AddRoleToUser("user11","USER");
                 accountService.AddRoleToUser("user22","USER");
                 accountService.AddRoleToUser("admin","ADMIN");
                 accountService.AddRoleToUser("admin","USER");

             };

        }


    }


