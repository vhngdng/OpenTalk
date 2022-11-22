package com.example.demo.service.Impl;

import com.example.demo.dto.BranchDTO;
import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.entity.OpenTalk;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OpenTalkRepository;
import com.example.demo.service.IOpenTalkService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = {Exception.class, Throwable.class})
public class OpenTalkService implements IOpenTalkService {
    @Autowired
    private OpenTalkRepository openTalkRepository;
    @Autowired
    private MapStructMapper mapStructMapper;
    @Autowired
    private BranchService branchService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private Faker faker;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<OpenTalkDisplayDTO> findAllOpenTalkDisplay() {
        List<OpenTalk> openTalks = openTalkRepository.findAll();
//        openTalks.forEach(openTalk -> openTalk
//                .setHostOpenTalk(hostOpenTalkService.findById(openTalk.getId())));
        return mapStructMapper.toDisplayDTO(openTalks);
    }

    @Override
    public OpenTalkDTO save(OpenTalkDTO model) {
//        OpenTalk openTalk = new OpenTalk();
        if (model.getId() == null) model.setId(0L);
        BranchDTO branchDTO = branchService.findById(model
                .getBranchDTO()
                .getId());
        model.setBranchDTO(branchDTO);
        if (model.getEmployeeDTO() == null) throw new EntityNotFoundException("Cannot find the host");
        EmployeeDTO employeeDTO = employeeService.findById(model.getEmployeeDTO().getId());
        model.setEmployeeDTO(employeeDTO);
        OpenTalk openTalk = mapStructMapper.toEntity(model);
        openTalk = openTalkRepository.save(openTalk);
        return mapStructMapper.toDTO(openTalk);
    }

    @Override
    public OpenTalkDTO updateOpenTalk(OpenTalkDTO model, Long id) {
        OpenTalk openTalkUpdate = openTalkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not existed id= " + id));
        mapStructMapper.updateOpenTalkFromDTO(model, openTalkUpdate);
        return mapStructMapper.toDTO(openTalkUpdate);

    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        openTalkRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<OpenTalkDisplayDTO>> findAllOpenTalksOfEmployee(Long id) {
        List<OpenTalkDisplayDTO> openTalkDisplayDTOS = mapStructMapper.toDisplayDTO(
                openTalkRepository
                        .findOpenTalksByEmployeeId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Employee id is not existed " + id)));
        return ResponseEntity.status(HttpStatus.OK).body(openTalkDisplayDTOS);
    }

    @Override
    public ResponseEntity<List<OpenTalkDTO>> findDetailedOpenTalksOfEmployee(Long id) {
        List<OpenTalkDTO> openTalkDTOS = mapStructMapper.toOpenTalkDTOs(
                openTalkRepository
                        .findOpenTalksByEmployeeId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Employee id is not existed " + id)));
        return ResponseEntity.status(HttpStatus.OK).body(openTalkDTOS);
    }


    public Page<OpenTalkDTO> findAll(Pageable pageable) {
        Page<OpenTalk> page = openTalkRepository.findAll(pageable);
        return page.map(mapStructMapper::toDTO);
    }

    public List<OpenTalkDTO> findAllOpenTalk() {
        return mapStructMapper.toOpenTalkDTOs(openTalkRepository.findAll());
    }


//    public void initEmployee() {
//        Random rd = new Random();
//        for (int i = 0; i < 50; i++) {
//            OpenTalk openTalk = new OpenTalk(
//                    faker.book().title(),
//                    LocalDateTime.now().minusDays(rd.nextInt(60*24*360*3)),
//                    faker.internet().domainName(),
//                    employeeRepository.findAll().get(rd.nextInt((int) employeeRepository.count())),
//                    branchRepository.findAll().get(rd.nextInt((int) branchRepository.count())));
//            openTalkRepository.save(openTalk);
//        }
//    }

}
