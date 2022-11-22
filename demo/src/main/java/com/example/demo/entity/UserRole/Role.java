package com.example.demo.entity.UserRole;

import com.example.demo.ENUM.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data

@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = true)
    private ERole name;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<Employee> employees = new ArrayList<>();

    public Role(ERole name) {
        this.name = name;
    }
}
