package com.example.demo.service.Impl;

import com.example.demo.dto.BranchDTO;
import com.example.demo.entity.Branch;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.BranchRepository;
import com.example.demo.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = {Exception.class, Throwable.class})
public class BranchService implements IBranchService {
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private MapStructMapper mapStructMapper;


    @Override
    public List<BranchDTO> findAllBranches() {
        return mapStructMapper.toBranchDTOs(branchRepository.findAll());

    }

    public BranchDTO save(BranchDTO branchDTO) {
        Branch branch = new Branch();
        if (branchDTO.getId() == null) {
            branch = branchRepository.save(mapStructMapper.toEntity(branchDTO));
        } else {
            branch = branchRepository.findById(branchDTO.getId())
                    .orElseThrow(() -> new DuplicateEntityException(
                            "This brand is existed, id= " + String.valueOf(branchDTO.getId())));
        }
        return mapStructMapper.toDTO(branch);
    }

    @Override
    public BranchDTO findById(Long id) {
        return mapStructMapper.toDTO(branchRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not existed id=" + id)));
    }

    @Override
    public long count() {
        return branchRepository.count();
    }

}
