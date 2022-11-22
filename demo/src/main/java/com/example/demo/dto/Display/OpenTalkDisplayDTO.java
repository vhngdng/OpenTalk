package com.example.demo.dto.Display;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

import javax.persistence.Column;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenTalkDisplayDTO {
    private Long id;
    private String topicName;
    private String branch;

}
