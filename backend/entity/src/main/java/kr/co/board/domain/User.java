package kr.co.board.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 사용자 정보 관련 Entity
 * Created by 권 오빈 on 2016. 7. 31..
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USER")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = -5940694792721955482L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "UNIQUE_ID")
	private UUID uniqueId;

	@Column(name = "EMAIL", length = 150, unique = true)
	@Email
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;

	@Column(name = "USER_NAME", nullable = false)
	private String userName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<UserRole> userRoleList;

}
