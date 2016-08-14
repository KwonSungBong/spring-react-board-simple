package kr.co.board.security.service;

import kr.co.board.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 4. 25..
 */
public interface CmmSocialAndUserDetailService extends UserDetailsService {
	UserDetails loadUserByUniqueId(UUID uuid) throws UsernameNotFoundException;
//	UserEntity loadUserByConnectionKey(ConnectionKey connectionKey);

//	void updateUserSocial(User user);

	User findByUserId(String userId);
}
