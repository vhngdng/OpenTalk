package com.example.demo.service.Impl;

import com.example.demo.dto.BranchDTO;
import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.entity.Employee;
import com.example.demo.entity.OpenTalk;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OpenTalkRepository;
import com.example.demo.service.IOpenTalkService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = {Exception.class, Throwable.class})
@Slf4j
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
    public void deleteById(Long id) {
        openTalkRepository.deleteById(id);
    }

    @Override


    public List<OpenTalkDisplayDTO> findAllOpenTalksOfEmployee(Long id) {
        return mapStructMapper.toDisplayDTO(
                openTalkRepository
                        .findOpenTalksByEmployeeId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Employee id is not existed " + id)));
    }

    @Override
    public List<OpenTalkDTO> findDetailedOpenTalksOfEmployee(Long id) {

        return mapStructMapper.toOpenTalkDTOs(
                openTalkRepository
                        .findOpenTalksByEmployeeId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Employee id is not existed " + id)));

    }

    @Override
    public Page<OpenTalkDTO> findAllWithPagination(Integer limit, Integer page) {
        if (page != null && limit != null) {
            Sort idSort = Sort.by("id");
            Pageable pageable = PageRequest.of(page - 1, limit, idSort);
            Page<OpenTalk> pageEmployee = openTalkRepository.findAll(pageable);
            return pageEmployee.map(mapStructMapper::toDTO);
        } else {
            return new PageImpl<>(findAllOpenTalk());
        }

    }

    @Override
    public List<OpenTalkDisplayDTO> findOpenTalkOfLoginUser(Principal userPrincipal) {
        String username = userPrincipal.getName();
        return mapStructMapper.toDisplayDTO(findByEmPloyeeName(username));
    }

    @Override
    public List<OpenTalk> findByEmPloyeeName(String username) {
        Employee employee = employeeRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("userName nay khong ton tai"));
        return openTalkRepository.findOpenTalksByEmployeeId(employee.getId())
                .orElseThrow(() -> new EntityNotFoundException("userName nay chua host openTalk"));
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
