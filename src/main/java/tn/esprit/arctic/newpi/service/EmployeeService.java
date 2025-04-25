package tn.esprit.arctic.newpi.service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.arctic.newpi.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    @Transactional
    void createEmployee(Employee e);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void updateEmployee(Long id, Map<String, Object> updates);
    void deleteEmployee(Long id);
    void evaluatePerformance(Long Id, Double newScore);
    List<Employee> getTopPerformers(int topN);
    List<Employee> findByEmail(String email);

    void updateEmployee(String fName, String lName, Map<String, Object> updates);

        void verifierEmployesActifs();
    //List<Employee> findAll();

}
