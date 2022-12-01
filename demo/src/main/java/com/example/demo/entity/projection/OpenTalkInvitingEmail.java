package com.example.demo.entity.projection;

import java.time.LocalDateTime;


public interface OpenTalkInvitingEmail {
  Long getId();

  String getTopicName();

  LocalDateTime getTime();

  String getLinkMeeting();

//  Branch getBranch();
}
