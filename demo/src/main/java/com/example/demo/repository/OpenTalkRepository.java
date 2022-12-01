package com.example.demo.repository;

import com.example.demo.entity.OpenTalk;
import com.example.demo.entity.projection.OpenTalkInvitingEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OpenTalkRepository extends JpaRepository<OpenTalk, Long> {
  @Query("select o from OpenTalk o where o.employee.id=:userId")
  Optional<List<OpenTalk>> findOpenTalksByEmployeeId(@Param("userId") Long userId);


  @Query(value = "SELECT o.id as id, o.link_meeting as linkMeeting, o.time as time, o.topic_title as topicName, o.branch_id as branch " +
          "FROM open_talk o " +
          "WHERE DATEDIFF(TIME, NOW()) = (" +
          "SELECT MIN(estimate_time) " +
          "FROM (" +
          "SELECT DATEDIFF(TIME, NOW()) AS estimate_time " +
          "FROM open_talk " +
          "HAVING estimate_time > 0) AS b)", nativeQuery = true)
  List<OpenTalkInvitingEmail> findNearestOpenTalk2();

  @Query(value = "select o from OpenTalk o where o.time between:monday and :sunday ")
  List<OpenTalk> findNearestOpenTalk(@Param("monday") LocalDateTime monday,@Param("sunday") LocalDateTime sunday);


}
