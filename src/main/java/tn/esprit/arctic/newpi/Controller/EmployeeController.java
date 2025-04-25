package tn.esprit.arctic.newpi.Controller;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.arctic.newpi.entity.Employee;

import tn.esprit.arctic.newpi.entity.LoginRequest;
import tn.esprit.arctic.newpi.repositories.EmployeeRepositories;
import tn.esprit.arctic.newpi.service.DepartmentService;
import tn.esprit.arctic.newpi.service.EmployeeService;
import tn.esprit.arctic.newpi.service.PresenceService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Data
@RequestMapping(value = "/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeRepositories employeeRepositories;
    @Autowired
    private PresenceService presenceService;




    @GetMapping("/employees/stats/{id}")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeRepositories.findById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Employee emp = optionalEmployee.get();
        Map<String, Object> data = new HashMap<>();
        data.put("absences", emp.getNombreAbsences());
        data.put("retards", emp.getNombreRetards());
        data.put("note", emp.getNotePerformance());

        return ResponseEntity.ok(data);
    }

    @PostMapping("/pointage")
    public ResponseEntity<String> pointerPresence(@RequestParam Long idEmploye) {
        Optional<Employee> optionalEmploye = employeeRepositories.findById(idEmploye);
        if (optionalEmploye.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé introuvable");
        }

        Employee emp = optionalEmploye.get();
        LocalDateTime now = LocalDateTime.now();

        // Vérifie si c'est déjà pointé aujourd’hui
        if (emp.getLastLogin() != null && emp.getLastLogin().toLocalDate().equals(now.toLocalDate())) {
            return ResponseEntity.badRequest().body("Déjà pointé aujourd'hui !");
        }

        emp.setLastLogin(now);
        employeeRepositories.save(emp);

        return ResponseEntity.ok("Pointage réussi à " + now.toString());
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build(); // succès sans contenu
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 si non trouvé
        }
    }

    @PostMapping("/employees")
    void createEmployee(@RequestBody Employee e){
        Employee employee =new Employee();
        employee.setFName(e.getfName());
        employee.setlName (e.getlName());
        employee.setRole(e.getRole());
        employee.setSalary(e.getSalary());
        employee.setPerformanceScore(e.getPerformanceScore());
/////////////
        employee.setMotDePasse(e.getMotDePasse());
        employee.setEmail(e.getEmail());
        this.employeeService.createEmployee(employee);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        List<Employee> foundEmployees = employeeRepositories.findByEmail(loginRequest.getEmail());

        if (!foundEmployees.isEmpty()) {
            Employee employee = foundEmployees.get(0); // on suppose qu’un seul user a cet email

            if (employee.getMotDePasse().equals(loginRequest.getMotDePasse())) {
                employee.setLastLogin(LocalDateTime.now());
                employee.setLastLogout(null);
                employee.setBesoinVerification(false);
                employee.setCodeVerification(null);
                employeeRepositories.save(employee);
                presenceService.marquerPresenceAutomatique(employee);

                return ResponseEntity.ok("Connecté avec succès");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");



        /*Employee employee = (Employee) employeeRepositories.findByEmail(loginRequest.getEmail());

        if (employee != null && employee.getMotDePasse().equals(loginRequest.getMotDePasse())) {
            employee.setLastLogin(LocalDateTime.now());
            employee.setBesoinVerification(false);
            employee.setCodeVerification(null);
            employeeRepositories.save(employee);

            return ResponseEntity.ok("Connecté avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }*/
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam Long idEmploye) {
        Employee employe = employeeRepositories.findById(idEmploye).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        employe.setLastLogout(now);
        employe.setBesoinVerification(false);
        employe.setCodeVerification(null);
        Duration duree = Duration.between(employe.getLastLogin(), now);
        long minutes = duree.toMinutes();
        if (employe.getTotalMinutesWorked() == null) employe.setTotalMinutesWorked(0L);
        employe.setTotalMinutesWorked(employe.getTotalMinutesWorked() + minutes);

        employeeRepositories.save(employe);
        return ResponseEntity.ok("Déconnexion réussie");
    }


   @PostMapping("/verifier-presence")
   public ResponseEntity<Map<String, String>> verifierCode(
           @RequestParam Long idEmploye,
           @RequestParam String code) {

       Map<String, String> response = new HashMap<>();

       Optional<Employee> optionalEmploye = employeeRepositories.findById(idEmploye);
       if (optionalEmploye.isEmpty()) {
           response.put("message", "Employé introuvable !");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
       }

       Employee employe = optionalEmploye.get();

       if (Boolean.TRUE.equals(employe.getBesoinVerification())
               && code.equals(employe.getCodeVerification())) {

           employe.setBesoinVerification(false);
           employe.setCodeVerification(null);
           employe.setHeureVerificationDemandee(null);
           employeeRepositories.save(employe);

           response.put("message", "Vérification réussie !");
           return ResponseEntity.ok(response);
       } else {
           LocalDateTime now = LocalDateTime.now();
           employe.setLastLogout(now);

           Duration duree = Duration.between(employe.getLastLogin(), now);
           long minutes = duree.toMinutes();
           if (employe.getTotalMinutesWorked() == null) employe.setTotalMinutesWorked(0L);
           employe.setTotalMinutesWorked(employe.getTotalMinutesWorked() + minutes);
           employe.setBesoinVerification(false);
           employe.setCodeVerification(null);
           employe.setHeureVerificationDemandee(null);
           employeeRepositories.save(employe);

           response.put("message", "Code incorrect. Vous avez été déconnecté.");
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
       }
   }
    @GetMapping("/verif-check")
    public boolean checkVerification(@RequestParam Long idEmploye) {
        Employee emp = employeeRepositories.findById(idEmploye).orElse(null);
        return emp != null && Boolean.TRUE.equals(emp.getBesoinVerification());
    }
/*
    @GetMapping("/verifier-code")
    public ResponseEntity<?> verifierBesoinDeCode(@RequestParam Long idEmploye) {
        Optional<Employee> optEmp = employeeRepositories.findById(idEmploye);

        if (optEmp.isPresent()) {
            Employee emp = optEmp.get();

            if (Boolean.TRUE.equals(emp.getBesoinVerification())) {
                Map<String, Object> res = new HashMap<>();
                res.put("besoin", true);
                res.put("code", emp.getCodeVerification());
                res.put("heureDemande", emp.getHeureVerificationDemandee());
                return ResponseEntity.ok(res);
            }
        }

        return ResponseEntity.ok(Map.of("besoin", false));
    }

*/




    @PutMapping("/employees/{id}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates // Accepte n'importe quel champ
    ) {
        try {
            employeeService.updateEmployee(id, updates);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
/*
    @PutMapping("/employees/update-by-name")
    public ResponseEntity<String> updateEmployeeByName(
            @RequestParam String fName,
            @RequestParam String lName,
            @RequestBody Map<String, Object> updates
    ) {
        try {
            employeeService.updateEmployee(fName, lName, updates);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
*/



    @GetMapping("/employees")
    List<Employee> getAllEmployees(){
        return this.employeeService.getAllEmployees();

    }

    @GetMapping("/employees/{id}")
    Employee getEmployeeById(@PathVariable long id){
        return this.employeeService.getEmployeeById(id);
    }


    @GetMapping("/employees/top")
    public List<Employee> getTopPerformers(@RequestParam int topN) {
        return this.employeeService.getTopPerformers(topN);
    }
}
