package com.example.demo.service.Impl;

import com.example.demo.ENUM.ERole;
import com.example.demo.dto.BranchDTO;
import com.example.demo.dto.Display.AccountDisplayDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.entity.UserRole.Role;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.IEmployeeService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
@Slf4j
@Transactional(rollbackOn = {Exception.class, Throwable.class})
public class EmployeeService implements IEmployeeService {

    //    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final MapStructMapper mapStructMapper;
    private final BranchService branchService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final BranchRepository branchRepository;
    private final Faker faker;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           MapStructMapper mapStructMapper,
                           BranchService branchService,
                           RoleRepository roleRepository,
                           BranchRepository branchRepository,
                           RoleService roleService,
                           Faker faker) {
        this.employeeRepository = employeeRepository;
        this.mapStructMapper = mapStructMapper;
        this.branchService = branchService;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.roleService = roleService;
        this.faker = faker;
//        initEmployee();
    }

    @Override
    public EmployeeDTO findById(long id) {
        Employee foundEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id is not exist"));
        //log.info(foundEmployee.getAudit().getCreatedAt().toString());
        return mapStructMapper.toDTO(foundEmployee);
    }

    @Override
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return new ArrayList<>(
                mapStructMapper.toEmployeeDTOs(
                        employeeRepository.findAll()));
    }

    @Override
    public Page<EmployeeDTO> findAllWithPagination(Integer limit, Integer page) {
        if (page != null && limit != null) {
            Sort idSort = Sort.by("id");
            Pageable pageable = PageRequest.of(page - 1, limit, idSort);
            Page<Employee> pageEmployee = employeeRepository.findAll(pageable);
            log.info(pageEmployee.getContent().get(0).getRoles().toString());
            return pageEmployee.map(mapStructMapper::toDTO);
        } else {
            return new PageImpl<>(findAllEmployees());
        }
    }

    @Override
    public List<AccountDisplayDTO> findAllAccount() {
        List<Employee> employees = employeeRepository.findAll();
        return mapStructMapper.toListAccountDisplayDTO(employees);
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        if (employeeDTO.getId() == null) employeeDTO.setId(0L);
        if (duplicateName(employeeDTO.getUserName())) {
            throw new DuplicateEntityException(
                    "This username has existed " + "\"" + employeeDTO.getUserName() + "\"");
        }
        if (duplicateEmail(employeeDTO.getEmail())) {
            throw new DuplicateEntityException(
                    "This email has existed " + "\"" + employeeDTO.getEmail() + "\"");
        }
        BranchDTO branchDTO = branchService
                .findById(employeeDTO
                        .getBranchDTO()
                        .getId());
        employeeDTO.setBranchDTO(branchDTO);
        employee = mapStructMapper.toEntity(employeeDTO);
//        employee.getAudit().setCreatedAt(LocalDateTime.now());
        employee = employeeRepository.save(employee);
        log.info("Saving new user to the database", employee.getFullName());
        return mapStructMapper.toDTO(employee);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO model, long id) {
        Employee employeeUpdate = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not existed id= " + id));
//        log.info("audit create at: " + employeeUpdate.getAudit().getLastUpdate().toString());
        if (employeeUpdate.getUserName()
                .equals(model.getUserName()))
            throw new DuplicateEntityException("Username is duplicate: " + model.getUserName());
        if (employeeUpdate.getEmail()
                .equals(model.getEmail()))
            throw new DuplicateEntityException("Email is duplicate: " + model.getEmail());
        mapStructMapper.updateEmployeeFromDTO(model, employeeUpdate);
//        Audit audit = new Audit();
//        audit.setLastUpdate(LocalDateTime.now());
//        employeeUpdate.getAudit().setLastUpdate(LocalDateTime.now());
//        employeeUpdate.setAudit(audit);
        if (employeeUpdate.getAudit() != null)
            log.info("audit create at: " + employeeUpdate.getAudit().getLastUpdate().toString());
        return mapStructMapper.toDTO(employeeUpdate);
    }

    @Override
    public boolean duplicateName(String name) {
        Optional<Employee> employee = employeeRepository.findByUserName(name);
        return employee.isPresent();
    }

    @Override
    public boolean duplicateEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee.isPresent();
    }
//
//    @Override
//    public Role saveRole(Role role) {
//        log.info("Saving new user to the database");
//
//        return null;
//    }

    @Override
    public void addRoleToUser(String username, ERole eRole) {
        log.info("Adding role to user", eRole.name());
        Employee employee = employeeRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("The user is not existed"));
        Role role = roleService.findByName(eRole);
        employee.addRole(role);
    }

    @Override
    public List<EmployeeDTO> findAllAdminAccount() {
        return mapStructMapper.toEmployeeDTOs(employeeRepository.getAllAdminAccount().orElseThrow(() -> new EntityNotFoundException("Danh sach Admin khong co")));
    }

//    @Override
//    public List<String> findAllEmailExceptHost(Long id) {
//        String removedEmail = employeeRepository
//                .findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Id is not exist: " + id))
//                .getEmail();
//        List<Employee> employees = employeeRepository.findAll();
//        List<String> emails = employees.stream().map(Employee::getEmail).collect(Collectors.toList());
//        emails.remove(removedEmail);
//        return emails;
//    }

//    @Override
//    public List<String> findAllFullNameExceptHost(Long id) {
//        String removedFullName = employeeRepository
//                .findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Id is not exist: " + id))
//                .getFullName();
//        List<Employee> employees = employeeRepository.findAll();
//        List<String> fullNames = employees.stream().map(Employee::getFullName).collect(Collectors.toList());
//        fullNames.remove(removedFullName);
//        return fullNames;
//    }
    @Override
    public List<EmployeeDTO> findAllExceptHost(Long id) {
        Employee host = employeeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id is not exist: " + id));
        List<Employee> employees = new ArrayList<>();
        employees = (List<Employee>) ((ArrayList) employeeRepository.findAll()).clone();
        employees.remove(host);
        return mapStructMapper.toEmployeeDTOs(employees);
    }

    @Override
    public long size() {
        return employeeRepository.count();
    }

    public EmployeeDTO findByName(String name) {
        return mapStructMapper.toDTO(employeeRepository
                .findByUserName(name).stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Not found name" + name))
        );
    }



    public void initEmployee() {
        Random rd = new Random();
        List<Role> roles;
        Role role;

        for (int i = 0; i < 50; i++) {
            roles = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                role = new Role();
                roles.add(roleRepository.findAll().get(0));
                if (rd.nextInt(2) == 1) roles.add(roleRepository.findAll().get(1));
            }
            Employee employee = new Employee(
                    faker.name().username(),
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.internet().password(),
                    new HashSet<>(roles),
                    rd.nextInt(2) == 1,
                    branchRepository.findAll().get(rd.nextInt((int) branchRepository.count())));
            employeeRepository.save(employee);
        }
    }
}
