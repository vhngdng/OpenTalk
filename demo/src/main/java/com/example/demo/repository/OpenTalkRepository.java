package com.example.demo.repository;

import com.example.demo.entity.OpenTalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpenTalkRepository extends JpaRepository<OpenTalk, Long> {
    @Query("select o from OpenTalk o where o.employee.id=:userId")
    Optional<List<OpenTalk>> findOpenTalksByEmployeeId(@Param("userId") Long userId);
}
