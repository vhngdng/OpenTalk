package com.example.demo.repository;

import com.example.demo.Scheduler.EmailEntity.MailSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MailScheduleRepository extends JpaRepository<MailSchedule, Integer> {
    Optional<MailSchedule> findByScheduleIdAndIsDeletedFalse(Integer scheduleId);

    Page<MailSchedule> findByUsernameAndIsDeletedFalse(String username, Pageable pageable);

    boolean existsByUsernameAndScheduleIdAndIsDeletedFalse(String username, Integer scheduleId);
}
