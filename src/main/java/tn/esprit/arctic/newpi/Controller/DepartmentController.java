package tn.esprit.arctic.newpi.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.arctic.newpi.entity.Department;

import tn.esprit.arctic.newpi.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping("/departments")
    void createDepartment(@RequestBody Department model) {
        Department department = new Department();
        department.setName(model.getName());
        this.departmentService.createDepartment(department);
    }
    @GetMapping("/departments")
    List<Department> Departments(){
        return this.departmentService.getAllDepartments();
    }
    @GetMapping("/departments/{id}")
    Department getDepartmentById(@PathVariable long id){
        return this.departmentService.getDepartmentById(id);

    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

}
