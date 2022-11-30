package com.example.demo.service.EmailService.Impl;

import com.example.demo.CONST.Constant;
import com.example.demo.Scheduler.EmailEntity.Email;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.service.EmailService.EmailService;
import com.example.demo.service.Impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;
  private final EmployeeService employeeService;

  @PostConstruct
  private void postConstruct() {
    log.info("preparing for inviting open talk");
  }

  @PreDestroy
  private void preDestroy() {
    log.info("Finishing inviting Open talk Job");
  }

  @Async
  @Override
  public void sendMail(Email email/*, Long branchId, OpenTalkDTO openTalkDTO*/) {
    List<String> emails = employeeService.findAllEmployees()
            .stream().map(EmployeeDTO::getEmail)
            .collect(Collectors.toList());
    emails.toArray(String[]::new);
    MimeMessagePreparator predator = mimeMessage -> {
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      helper.setSubject(email.getSubject());
      helper.setText(email.getText());
//            helper.addBcc("dungv0612@gmail.com");
      helper.setFrom("dungvh0612@gmail.com");
      int turn = 0;
      for (String s : emails) {
        helper.addBcc(s);
        turn++;
        if (turn == 10) break;
      }
    };
    mailSender.send(predator);
    log.info("Job completed" + new Date());

  }


  public void sendEmailInviteOpenTalkFromTemplate(OpenTalkDTO openTalkDTO) {
    Long id = openTalkDTO.getEmployeeDTO().getId();
    List<EmployeeDTO> employeeDTOs = employeeService.findAllEmployees();
//        List<String> fullNames = employeeService.findAllFullNameExceptHost(id);
    Locale locale = Locale.forLanguageTag(Constant.LANGUAGE_TAG);
    Context context = new Context(locale);
    context.setVariable(Constant.RECIPIENT, employeeDTOs);
    context.setVariable(Constant.TOPIC, openTalkDTO);


  }
}
