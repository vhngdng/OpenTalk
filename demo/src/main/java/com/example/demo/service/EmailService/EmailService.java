package com.example.demo.service.EmailService;

import com.example.demo.Scheduler.EmailEntity.Email;
import com.example.demo.dto.OpenTalkDTO;

public interface EmailService {
   void sendMail(Email email, OpenTalkDTO openTalkDTO);


}
