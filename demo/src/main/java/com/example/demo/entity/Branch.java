package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true, length = 64)
    private String name;



    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "branch"                                 //branch - employess
    )
    private List<Employee> employees;
//
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "branch"
    )
    private List<OpenTalk> openTalks;                        //branch- open talks





    public Branch(String address, String name) {
        this.address = address;
        this.name = name;
    }
}
