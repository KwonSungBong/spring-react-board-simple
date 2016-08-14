package kr.co.board.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ksb on 2016-08-07.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "REPLY")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "CONTENT")
    private String content;

    @CreatedBy
    private User createdUser;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private User lastModifiedUser;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
