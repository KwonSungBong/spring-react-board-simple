package kr.co.board.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by ksb on 2016-08-07.
 */
@Data
@Entity
@Table(name = "IMAGE")
@DynamicUpdate
public class Image {
    @Id
    @Column(name = "IMAGE_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageIdx;

    @ManyToOne
    private ImageGroup imageGroup;

    /**
     * 이미지 원본 명
     */
    @Column(name = "IMAGE_NAME", nullable = false)
    private String imageName;

    /**
     * 이미지 파일 사이즈
     */
    @Column(name = "IMAGE_SIZE", nullable = false)
    private long imageSize;

    /**
     * 저장된 이미지 명
     */
    @Column(name = "STORED_IMAGE_NAME", nullable = false)
    private String storedImageName;

    /**
     * 이미지 컨텐츠 타입
     */
    @Column(name = "CONTENT_TYPE")
    private String contentType;

    /**
     * 사용 여부
     */
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    /**
     * 정렬 순서
     */
    @Column(name = "SORT_ORDER")
    private int sortOrder = 1;
}
