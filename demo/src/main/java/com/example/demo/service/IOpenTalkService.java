package com.example.demo.service;

import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.OpenTalkDTO;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IOpenTalkService {
    List<OpenTalkDisplayDTO> findAllOpenTalkDisplay();


    OpenTalkDTO save(OpenTalkDTO model);

    OpenTalkDTO updateOpenTalk(@NotNull OpenTalkDTO model, Long id);

    ResponseEntity<Void> deleteById(Long id);

    ResponseEntity<List<OpenTalkDisplayDTO>> findAllOpenTalksOfEmployee(Long id);

    ResponseEntity<List<OpenTalkDTO>> findDetailedOpenTalksOfEmployee(Long id);
}
