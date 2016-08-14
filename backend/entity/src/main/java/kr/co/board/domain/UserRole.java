package kr.co.board.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 사용자 권한 관련 Entity
 * Created by 권 오빈 on 2016. 7. 31..
 */
@Entity
@Table(name = "USER_ROLE")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1936508833545856823L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ROLE_IDX")
	private int userRoleIdx;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIQUE_ID", nullable = false)
	private User user;

	@Column(name = "ROLE", nullable = false, length = 45)
	private String role;

}
