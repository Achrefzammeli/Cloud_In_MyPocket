package tn.esprit.arctic.newpi.service;

import tn.esprit.arctic.newpi.entity.Department;

import java.util.List;

public interface DepartmentService {
    void createDepartment(Department d);


    Department getDepartmentById(Long id);
    List<Department> getAllDepartments();
    Department updateDepartment(Long id, String newName  /*, List<String> newServices*/);
    void deleteDepartment(Long id);


// void addServiceToDepartment(Long departmentId, String service);
//void removeServiceFromDepartment(Long departmentId, String service);

}
