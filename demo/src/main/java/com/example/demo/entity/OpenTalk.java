package com.example.demo.entity;


import com.example.demo.Audit.Audit;
import com.example.demo.entity.projection.OpenTalkInvitingEmail;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Table(name = "openTalk")
@EntityListeners(AuditingEntityListener.class)



public class OpenTalk implements OpenTalkInvitingEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "topic_title")
    private String topicName;
    @Column(name = "time")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss")
    private LocalDateTime time;
    @Column(name = "link_meeting", nullable = false)
    private String linkMeeting;

    @Embedded
    private Audit audit;

//    @OneToOne(fetch = FetchType.LAZY,
//            cascade = {CascadeType.ALL},
//            orphanRemoval = true,
//            mappedBy = "openTalk"
//    )
////    @JoinColumn(name = "host_id")
//    private HostOpenTalk hostOpenTalk;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                     CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "opentalk_employee",
            joinColumns =
                    {@JoinColumn(name = "opentalk_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "employee_id", referencedColumnName = "id")})
    private Employee employee;
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "branch_id", nullable = false)             // open talks - branch
    private Branch branch;



    public OpenTalk(String topicName, LocalDateTime time, String linkMeeting, Employee employee, Branch branch) {
        this.topicName = topicName;
        this.time = time;
        this.linkMeeting = linkMeeting;
        this.employee = employee;
        this.branch = branch;
    }


}
