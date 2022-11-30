package com.example.demo.Scheduler.job;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class InviteOpenTalkJob implements Job {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<Employee> employees = employeeRepository.findAll();
        List<String> emails = employees.stream().map(Employee::getEmail).collect(Collectors.toList());

    }
}
