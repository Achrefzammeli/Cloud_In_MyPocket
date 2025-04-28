package tn.esprit.cloud_in_mypocket.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.cloud_in_mypocket.dto.ContractDTO;
import tn.esprit.cloud_in_mypocket.entity.Contract;
import tn.esprit.cloud_in_mypocket.entity.User;
import tn.esprit.cloud_in_mypocket.entity.Role;
import tn.esprit.cloud_in_mypocket.repository.ContractRepository;
import tn.esprit.cloud_in_mypocket.repository.UserRepository;
import tn.esprit.cloud_in_mypocket.service.FileStorageService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }



    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContract(@PathVariable Long id) {
        return contractRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createContract(
            @RequestPart("metadata") ContractDTO contractDTO,
            @RequestPart("file") MultipartFile file
    ) {
        System.out.println("Received DTO: " + contractDTO);
        try {
            if (contractDTO.getDeadline() == null || contractDTO.getDeadline().isEmpty()) {
                return ResponseEntity.badRequest().body("Deadline is required");
            }

            // Save file and get file path
            String filePath = fileStorageService.save(file);

            // Create contract entity
            Contract contract = new Contract();
            contract.setContent(contractDTO.getContent());
            contract.setDeadline(LocalDate.parse(contractDTO.getDeadline()).atStartOfDay());
            contract.setStatus(contractDTO.getStatus());
            contract.setPdfUrl(filePath);

            // Fetch lawyer and client users
            User lawyer = userRepository.findByIdAndRole(contractDTO.getLawyerId(), Role.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Lawyer not found"));

            User client = userRepository.findByIdAndRole(contractDTO.getClientId(), Role.CLIENT)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            // Associate the lawyer and client to the contract
            contract.setLawyer(lawyer); // You need to have setLawyer(User lawyer) in Contract entity
            contract.setClient(client); // You need to have setClient(User client) in Contract entity

            // Save contract
            Contract saved = contractRepository.save(contract);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving contract: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable Long id) {
        contractRepository.deleteById(id);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Contract>> getContractsByClient(@PathVariable Long clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (client.getRole() != Role.CLIENT) {
            return ResponseEntity.badRequest().build();
        }

        List<Contract> contracts = contractRepository.findByClientId(clientId);
        return ResponseEntity.ok(contracts);
    }

    // Stats endpoint
    @GetMapping("/stats/status-distribution")
    public Map<String, Long> getContractStatusDistribution(
            @RequestParam(required = false) Long lawyerId,
            @RequestParam(required = false) Long clientId) {

        List<Contract> contracts;

        if (clientId != null) {
            contracts = contractRepository.findByClientId(clientId);
        } else if (lawyerId != null) {
            contracts = contractRepository.findByLawyerId(lawyerId);
        } else {
            contracts = contractRepository.findAll();
        }

        return contracts.stream()
                .collect(Collectors.groupingBy(
                        Contract::getStatus,
                        Collectors.counting()
                ));
    }

}
