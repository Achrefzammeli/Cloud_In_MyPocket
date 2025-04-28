package tn.esprit.cloud_in_mypocket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.cloud_in_mypocket.entity.Contract;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAll();
    Contract save(Contract contract);
    Optional<Contract> findById(Long id);
    void deleteById(Long id);
    List<Contract> findByLawyerId(Long lawyerId);
    List<Contract> findByClientId(Long clientId);
    List<Contract> findByLawyerIdAndClientId(Long lawyerId, Long clientId);

    @Query("SELECT c FROM Contract c WHERE c.status = 'PENDING' AND c.deadline < :currentTime")
    List<Contract> findPendingContractsWithExpiredDeadline(LocalDateTime currentTime);

    @Query("SELECT c FROM Contract c WHERE c.status IN :statuses")
    List<Contract> findByStatusIn(List<String> statuses);
}
