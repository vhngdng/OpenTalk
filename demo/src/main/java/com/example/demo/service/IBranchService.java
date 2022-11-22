package com.example.demo.service;

import com.example.demo.dto.BranchDTO;

import java.util.List;

public interface IBranchService {
    List<BranchDTO> findAllBranches();
    BranchDTO save(BranchDTO branchDTO);

    BranchDTO findById(Long id);

    long count();
}
