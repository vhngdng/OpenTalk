package com.example.demo.controller;

import com.example.demo.dto.Display.AccountDisplayDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.Impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin")
//@RequestMapping("${path_admin}")   // use properties file
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

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
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @NotNull @RequestBody() EmployeeDTO model,
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
        return employeeService.findAllAdminAccount();
    }
}
