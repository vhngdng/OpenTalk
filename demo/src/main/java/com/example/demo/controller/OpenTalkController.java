package com.example.demo.controller;

import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.service.Impl.OpenTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/open-talks")
public class OpenTalkController {
    @Autowired
    private OpenTalkService openTalkService;


    @GetMapping()        //find all opentalk with a few details
    public ResponseEntity<List<OpenTalkDisplayDTO>> findAllAccount() {
        List<OpenTalkDisplayDTO> openTalkDisplayDTOS = openTalkService.findAllOpenTalkDisplay();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(openTalkDisplayDTOS);
    }

    @GetMapping("/managed-open-talks") //(path: /managed-open-talks/limit=2?page=2)
    public ResponseEntity<Page<OpenTalkDTO>> showAllOpenTalksWithPagination(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = false) Integer page) {

//        Output output = new Output();
        if (page != null && limit != null) {
            Sort idSort = Sort.by("id");
            Pageable pageable = PageRequest.of(page - 1, limit, idSort);
            Page<OpenTalkDTO> pageResult = openTalkService.findAll(pageable);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pageResult);
        } else {
            Page<OpenTalkDTO> pageAll = new PageImpl<>(openTalkService.findAllOpenTalk());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pageAll);
        }
    }

    @GetMapping("/employee_id/{id}")
    public ResponseEntity<List<OpenTalkDisplayDTO>> showAllOpenTalkByEmployeeId(@NotNull @PathVariable Long id) {
        return openTalkService.findAllOpenTalksOfEmployee(id);
    }

    @GetMapping("/admin/employee_id/{id}")
    public ResponseEntity<List<OpenTalkDTO>> showDetailedByEmployeeId(@NotNull @PathVariable Long id) {
        return openTalkService.findDetailedOpenTalksOfEmployee(id);
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
        return openTalkService.deleteById(id);
    }


}

