package com.example.demo.controller;

import com.example.demo.dto.Display.AccountDisplayDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.Impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin")
//@RequestMapping("${path_admin}")   // use properties file
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/accounts")        //find all account with a few details
    public ResponseEntity<List<AccountDisplayDTO>> findAllAccount() {
        List<AccountDisplayDTO> accountDisplayDTO = employeeService.findAllAccount();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountDisplayDTO);
    }

    @GetMapping("/managedEmployees") //(path: /managed-employees/limit=2?page=2)
    public ResponseEntity<Page<EmployeeDTO>> showAllEmployeeWithPagination(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = false) Integer page) {
        logger.info("================================================");
        Page<EmployeeDTO> pageResult = employeeService.findAllWithPagination(limit, page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findById(id));
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO model) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.save(model));
    }

    @PutMapping("/employee/{id}")
    @Transactional(propagation = Propagation.MANDATORY,
     isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<EmployeeDTO> updateEmployee(@NotNull @RequestBody EmployeeDTO model,
                                                      @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.updateEmployee(model, id));
    }

    @DeleteMapping("/employee/{id}")                // delete employee
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        logger.info(employeeService.findById(id).toString());
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/adminAccount")
    public ResponseEntity<List<EmployeeDTO>> findAllAdminAccount() {
        return ResponseEntity.ok(employeeService.findAllAdminAccount());
    }
}
