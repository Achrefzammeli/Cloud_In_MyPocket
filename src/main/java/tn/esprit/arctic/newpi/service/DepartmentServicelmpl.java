package tn.esprit.arctic.newpi.service;

import org.springframework.stereotype.Service;
import tn.esprit.arctic.newpi.entity.Department;
import tn.esprit.arctic.newpi.repositories.DepartmentRepositories;

import java.util.List;

@Service
public class DepartmentServicelmpl implements DepartmentService {
    final private DepartmentRepositories departmentRepositories;
    public DepartmentServicelmpl(DepartmentRepositories departmentRepositories){
    this.departmentRepositories = departmentRepositories;

    }
    @Override
    public void createDepartment(Department d) {
        this.departmentRepositories.save( d );
    }

    @Override
    public Department getDepartmentById(Long id) {
        return this.departmentRepositories.findById(id).get();
    }

    @Override
    public List<Department> getAllDepartments() {
        return this.departmentRepositories.findAll();
    }

    @Override
    public Department updateDepartment(Long id, String newName) {
        return null;
    }

    @Override
    public void deleteDepartment(Long id) {

        if (!departmentRepositories.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        departmentRepositories.deleteById(id);

    }


}
