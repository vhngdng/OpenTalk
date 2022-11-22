package com.example.demo.Audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Embeddable

//@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Audit {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime lastUpdate;
//    @ManyToOne(fetch = FetchType.EAGER,
//    cascade = CascadeType.ALL)
//    @JoinColumn(name = "updated_by")
//    @LastModifiedBy
//    @CreatedBy
//    private Employee employee;

    @LastModifiedBy
    @Column(name = "upadted_by")
//    @CreatedBy
//    @JsonProperty("updated_by")
    private String userName;




//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void lastUpdate( ) {
//        this.lastUpdate = LocalDateTime.now();
//    }
}
