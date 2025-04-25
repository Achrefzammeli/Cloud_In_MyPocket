package tn.esprit.arctic.newpi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.arctic.newpi.entity.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
@Repository
public interface EmployeeRepositories extends JpaRepository<Employee, Long>{

    List<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e ORDER BY e.performanceScore DESC")
    Page<Employee> findAllOrderByPerformanceScoreDesc(Pageable pageable);
}
