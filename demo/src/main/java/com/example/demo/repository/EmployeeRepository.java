package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e")
    List<Employee> getAllEmployee ();

//    @Query(value = "select e from Employee e where e.roles.size=2")
    @Query(value = "select * from employee e " +
            "JOIN user_role ur On ur.user_id = e.id " +
            "JOIN role r On r.id = ur.role_id " +
            "WHERE r.id = 2" +
            "  " , nativeQuery = true)
    Optional<List<Employee>> getAllAdminAccount();
    @Query(value = ("Select * from employee where employee.username =:name"),nativeQuery = true)
    Optional<Employee> findByUserName (@Param("name") String name);

    Optional<Employee> findByEmail(String email);
//    Optional<Employee> findByUsernameOrEmail(String username, String email);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
