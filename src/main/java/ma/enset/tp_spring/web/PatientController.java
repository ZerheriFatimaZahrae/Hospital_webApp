package ma.enset.tp_spring.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import ma.enset.tp_spring.entites.Patient;
import ma.enset.tp_spring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller @AllArgsConstructor
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;
    @GetMapping("/index")
    //@RequestParam : seginifier va chercher un att son nom est page dans url et l affected son valeur  a attr page
    public String index(Model model, @RequestParam (name = "page",defaultValue = "0") int page
            ,@RequestParam (name = "size",defaultValue = "4") int size
            ,@RequestParam (name = "keyword",defaultValue = "") String kw){
        //afficher la page au quel contient keyword et son page et size , kw on a pris pas input
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw,PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatients.getContent());//afficher la page dans num de page et size deja definie
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);//tab represente nb de pages
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(Long id,
                         @RequestParam (name = "keyword",defaultValue = "") String keyword,
                         @RequestParam (name = "page",defaultValue = "0") int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/edit")
    public String edit(Long id ,
                       Model model,
                         String keyword,
                        int page
                         ){
        Patient patient=patientRepository.findById(id).orElse(null);
        if (patient==null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);

        return "editPatient";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
    @GetMapping("/patients")
    public List<Patient> listPatient(){
        return patientRepository.findAll();
    }

    @GetMapping("/formPatients")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping(path = "/save")
    //@valid : annoncer qd doit verifier les attrs de patient
    public String save(Model model ,
                       @Valid Patient patient ,
                       BindingResult bindingResult
                       ,@RequestParam (name = "page",defaultValue = "0") int page
                        ,@RequestParam (name = "keyword",defaultValue = "") String kw){
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+kw;
    }

}
