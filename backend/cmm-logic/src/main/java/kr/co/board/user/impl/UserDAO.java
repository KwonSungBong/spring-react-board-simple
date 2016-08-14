package kr.co.board.user.impl;

import kr.co.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("userDAO")
public interface UserDAO extends JpaRepository<User, UUID>{

	/**
	 * 이메일로 유저 1개 가져오기
	 * @param email
	 * @return 이메일에 맞는 User
	 */
	User findByEmail(String email);
}
