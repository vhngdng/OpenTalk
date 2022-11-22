package com.example.demo.entity;

import com.example.demo.Audit.Audit;
import com.example.demo.entity.UserRole.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "employee")
//@EntityListeners(value = JpaAuditingConfig.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;
    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String userName;

    @Column(name = "full_name", length = 64, nullable = false)
    private String fullName;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    @Email
    private String email;
    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Embedded
//    @AttributeOverrides(value = {
//            @AttributeOverride(name = "id", column = @Column(name = "house_number")),
//            @AttributeOverride(name = "password", column = @Column(name = "street"))
//    })
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Audit audit;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,
                    CascadeType.PERSIST, CascadeType.REFRESH})

    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OpenTalk> openTalks;

    @Column(columnDefinition = "boolean default true")
    private boolean active;


    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;


//    @OneToOne(mappedBy = "employee",
//            cascade = {CascadeType.DETACH, CascadeType.MERGE,
//                    CascadeType.PERSIST, CascadeType.REFRESH})
//    private HostOpenTalk hostOpenTalk;


    public void addOpenTalk(OpenTalk openTalk) {
        this.openTalks.add(openTalk);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public Employee(String userName, String fullName, String email, String password, Set<Role> roles, boolean active, Branch branch) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.active = active;
        this.branch = branch;
    }


}