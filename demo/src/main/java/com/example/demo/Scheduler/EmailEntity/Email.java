package com.example.demo.Scheduler.EmailEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String from;
    private String to;
    private String subject;
    private String text;
    private String[] bcc;
    private String[] cc;
    private List<Objects> attachments;
    private Map<String, Object> props;

}
