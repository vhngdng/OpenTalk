package com.example.demo;

import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OpenTalkRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.Impl.EmployeeService;
import com.example.demo.service.Impl.RoleService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@Rollback(value = false)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(loader= AnnotationConfigContextLoader.class,classes = EmployeeRepositoryTests.class)
@MockBeans({@MockBean(EmployeeRepository.class),
        @MockBean(RoleRepository.class),
        @MockBean(EmployeeService.class)})
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MapStructMapper mapper;
    @MockBean
    private BranchRepository branchRepository;


    @MockBean
    private OpenTalkRepository openTalkRepository;
    @Autowired
    private RoleRepository roleRepository;
    @MockBean
    private RoleService roleService;
    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private Faker faker;


//    @Test
//    public void testCreateEmployee() {
//        Employee employee = new Employee();
//        employee.setEmail("rav3232@gmail.com");
//        employee.setActive(true);
//        employee.setUserName("test2222222");
//        employee.setBranch(branchRepository.getReferenceById(1L));
//
//        employee.setRoles(new HashSet<>(roleRepository.findAll()));
////        employee.addRole(roleRepository.findById(1).orElseThrow());
//        employee.setFullName("test");
//        employee.setPassword("test");
//        employee.setOpenTalks(openTalkRepository.findAll());
//        Employee saveEmployee = employeeRepository.save(employee);
//        Employee existEmployee = entityManager.find(Employee.class, saveEmployee.getId());
//        assertThat(existEmployee.getEmail()).isEqualTo(employee.getEmail());
//    }

//    @Test
//    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//    @Rollback(value = false)
//    public void testInitEmployee() {
//         Random rd = new Random();
//        List<Role> roles;
//        Role role;
//
//        for (int i = 0; i < 50; i++) {
//            roles = new ArrayList<>();
//            for (int j = 0; j < 2; j++) {
//                role = new Role();
//                roles.add(roleRepository.findAll().get(0));
//                if (rd.nextInt(2) == 2) roles.add(roleRepository.findAll().get(1));
//            }
//            Employee employee = new Employee(
//                    faker.name().username(),
//                    faker.name().fullName(),
//                    faker.internet().emailAddress(),
//                    faker.internet().password(),
//                    new HashSet<>(roles),
//                    rd.nextInt(2) == 1,
//                    branchRepository.findAll().get(rd.nextInt((int) branchRepository.count())));
//            employeeRepository.save(employee);
////        }
//        employeeService.initEmployee();
//        assertThat(employeeRepository
//                .findAll()
//                .stream()
//                .allMatch(employee1 ->
//                        employee1.getRoles().contains(roleRepository.findById(1).get()) ));
//    }

//    @Test
//    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//    @Rollback(value = false)
//    public void testInitOpenTalk() {
//        Random rd = new Random();
//        OpenTalk openTalk;
//        for (int i = 0; i < 50; i++) {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            OpenTalk openTalkTest = new OpenTalk(
//                    faker.book().title(),
//                    LocalDateTime.now().minusMinutes(rd.nextInt(60*24*360*3)),
//                    faker.internet().domainName(),
//                    employeeRepository.findAll().get(rd.nextInt((int) employeeRepository.count())),
//                    branchRepository.findAll().get(rd.nextInt((int) branchRepository.count())));
//            openTalkRepository.save(openTalkTest);
//        }


    @Test
    public ResponseEntity<OpenTalkDTO> findNearest () {
        OpenTalkDTO openTalkDTO = mapper.toDTO(openTalkRepository.findNearestOpenTalk());
        return ResponseEntity.ok(openTalkDTO);
    }
    }
//}
