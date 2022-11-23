package com.example.demo.controller;

import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.service.Impl.OpenTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/open-talks")
@PreAuthorize("isAuthenticated()")
public class OpenTalkController {
    @Autowired
    private OpenTalkService openTalkService;


    @GetMapping("/employee/all-opentalks")        //find all opentalk with a few details
    public ResponseEntity<List<OpenTalkDisplayDTO>> findAllOpenTalk() {
        List<OpenTalkDisplayDTO> openTalkDisplayDTOS = openTalkService.findAllOpenTalkDisplay();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(openTalkDisplayDTOS);
    }

    @GetMapping("/managed-open-talks") //(path: /managed-open-talks/limit=2?page=2)
//    @PostFilter("hasRole('ADMIN') or filterObject.name == authentication.name")
    public ResponseEntity<Page<OpenTalkDTO>> showAllOpenTalksWithPagination(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = false) Integer page) {
        Page<OpenTalkDTO> pageResult = openTalkService.findAllWithPagination(limit, page);
//        Output output = new Output();
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


}

