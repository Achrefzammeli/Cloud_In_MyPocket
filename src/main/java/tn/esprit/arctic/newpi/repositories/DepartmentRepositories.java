package tn.esprit.arctic.newpi.repositories;
/*package repositories;*/


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.arctic.newpi.entity.Department;

@Transactional
@Repository
public interface DepartmentRepositories extends JpaRepository<Department, Long > {

}
