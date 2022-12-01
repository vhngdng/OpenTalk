package com.example.demo.controller;

import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.OpenTalkRepository;
import com.example.demo.service.Impl.OpenTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/open-talks")
@PreAuthorize("isAuthenticated()")
public class OpenTalkController {
    @Autowired
    private OpenTalkService openTalkService;
    @Autowired
    private MapStructMapper mapStructMapper;
    @Autowired
    private OpenTalkRepository openTalkRepository;

    @GetMapping("/employee/all-opentalks")        //find all opentalk with a few details
    public ResponseEntity<List<OpenTalkDisplayDTO>> findAllOpenTalk() {
        List<OpenTalkDisplayDTO> openTalkDisplayDTOS = openTalkService.findAllOpenTalkDisplay();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(openTalkDisplayDTOS);
    }

    @GetMapping("/managed-open-talks") //(path: /managed-open-talks/limit=2?page=2)
    public ResponseEntity<Page<OpenTalkDTO>> showAllOpenTalksWithPagination(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = false) Integer page) {
        Page<OpenTalkDTO> pageResult = openTalkService.findAllWithPagination(limit, page);
       return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/employee_id/{id}")
    public ResponseEntity<List<OpenTalkDisplayDTO>> showAllOpenTalkByEmployeeId(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(openTalkService.findAllOpenTalksOfEmployee(id));
    }

    @GetMapping("/admin/employee_id/{id}")
    public ResponseEntity<List<OpenTalkDTO>> showDetailedByEmployeeId(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(openTalkService.findDetailedOpenTalksOfEmployee(id));
    }


    @PostMapping("/opentalk")
    public ResponseEntity<OpenTalkDTO> createOpenTalk(@NotNull @RequestBody OpenTalkDTO model) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(openTalkService.save(model));
    }

    @PutMapping("/opentalk/{id}")
    public ResponseEntity<OpenTalkDTO> updateEmployee(
            @NotNull @RequestBody OpenTalkDTO model,
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(openTalkService.updateOpenTalk(model, id));
    }

    @DeleteMapping("/opentalk/{id}")                // delete employee
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        openTalkService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/testnhanh")
    public ResponseEntity<List<OpenTalkDTO>> findNearest () {

        LocalDateTime monday = LocalDate
                .now()
                .minusDays(LocalDate.now().get(ChronoField.DAY_OF_WEEK) - 1)
                .atStartOfDay();
        LocalDateTime sunday = LocalDate
                .now()
                .plusDays(7 - LocalDate.now().get(ChronoField.DAY_OF_WEEK))
                .atStartOfDay();

        List<OpenTalkDTO> openTalkDTO = mapStructMapper.toOpenTalkDTOs(openTalkRepository.findNearestOpenTalk(monday, sunday));
        System.out.println(openTalkDTO);
        return ResponseEntity.ok(openTalkDTO);
    }

    @GetMapping("/testnhanh2")
    public ResponseEntity<List<OpenTalkDTO>> findNearestTest () {

        List<OpenTalkDTO> openTalkDTO = new ArrayList<>();
        openTalkRepository.findNearestOpenTalk2();
        System.out.println(openTalkDTO);
        return ResponseEntity.ok(openTalkDTO);
    }

}

