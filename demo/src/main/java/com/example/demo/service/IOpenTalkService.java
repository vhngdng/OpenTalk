package com.example.demo.service;

import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.entity.OpenTalk;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

public interface IOpenTalkService {
    List<OpenTalkDisplayDTO> findAllOpenTalkDisplay();


    OpenTalkDTO save(OpenTalkDTO model);

    OpenTalkDTO updateOpenTalk(@NotNull OpenTalkDTO model, Long id);

    void deleteById(Long id);

    List<OpenTalkDisplayDTO> findAllOpenTalksOfEmployee(Long id);

    List<OpenTalkDTO> findDetailedOpenTalksOfEmployee(Long id);

    Page<OpenTalkDTO> findAllWithPagination(Integer limit, Integer page);

    List<OpenTalkDisplayDTO> findOpenTalkOfLoginUser(Principal userPrincipal);

    List<OpenTalk> findByEmPloyeeName(String username);
}
