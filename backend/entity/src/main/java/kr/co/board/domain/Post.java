package kr.co.board.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by ksb on 2016-08-06.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "POST")
public class Post {

    public final static String REQUIRED_VALID_MESSAGE = "필수 입력값입니다.";
    public final static String SUBJECT_VALID_MESSAGE = "1글자 이상 입력하세요.";
    public final static int SUBJECT_MIN = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX")
    private Long idx;

    @NotNull(message = REQUIRED_VALID_MESSAGE)
    @Size(min = SUBJECT_MIN, message = SUBJECT_VALID_MESSAGE)
    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @OneToOne
    @JoinColumn(name = "PHOTO")
    private ImageGroup imageGroup;

    @CreatedBy
    private User createdUser;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private User lastModifiedUser;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public void setSubject(String subject){
        if(subject != null){
            this.subject = subject.trim();
        } else {
            this.subject = subject;
        }
    }
}
