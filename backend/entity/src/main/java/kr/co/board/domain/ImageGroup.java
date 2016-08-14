package kr.co.board.domain;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ksb on 2016-08-07.
 */
@Entity
@Table(name = "IMAGE_GROUP")
@Data
public class ImageGroup {
    @Id
    @Column(name = "IMAGE_GROUP_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageGroupIdx;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "imageGroup", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @OrderBy("sortOrder desc, imageIdx asc")
    @Where(clause = " enabled = true ")
    private List<Image> imageList;
}
