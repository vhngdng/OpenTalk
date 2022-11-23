package com.example.demo.controller;

import com.example.demo.dto.BranchDTO;
import com.example.demo.service.Impl.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/branch")
    @PreAuthorize("hasRole('(ADMIN)')")
    public ResponseEntity<BranchDTO> createBranch(@NotNull @RequestBody BranchDTO model) {
        BranchDTO branchDTO = branchService.save(model);
        return ResponseEntity.status(HttpStatus.OK).body(branchDTO);
    }

    @GetMapping("/managed-branches")
    public ResponseEntity<List<BranchDTO>> findAllBranches() {
        List<BranchDTO> branches = branchService.findAllBranches();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(branches);
    }

//    @PostMapping("/managed-branch")
//    public ResponseEntity<BranchDTO> createBranch(@NotNull @RequestBody BranchDTO model) {
//        branchService.save(model);
//        return ResponseEntity.status(HttpStatus.OK).body(model);
//    }
}
