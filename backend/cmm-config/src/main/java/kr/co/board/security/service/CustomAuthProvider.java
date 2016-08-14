package kr.co.board.security.service;

import kr.co.board.domain.User;
import kr.co.board.security.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 권 오빈 on 2016. 1. 26
 */
@Slf4j
@Component
public class CustomAuthProvider implements AuthenticationProvider {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CmmSocialAndUserDetailService userDetailsService;

	@Resource(name = "securityUserDAO")
	private SecurityUserDAO userDAO;

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Transactional
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("Authentication : {} ", authentication.toString());
		String email = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		log.debug("비밀번호: {}", password);

		log.debug("사용자가 입력한 로그인 정보 입니다. {}", Arrays.asList(email, passwordEncoder.encode(password)));

		List<User> userEntityList = userDAO.findByEmailAndEnabled(email, true);

		if(userEntityList.size() == 0){
			throw new BadCredentialsException("사용자 정보가 없습니다.");
		}

		User user = userEntityList.get(0);

//		현재 평문으로 저장되어 있음
//		if(!passwordEncoder.matches(password, user.getPassword())){
//			throw new BadCredentialsException("사용자 정보가 없습니다.");
//		}
		log.debug("최초 권한 : {}", user.getUserRoleList().toString());
		CmmUserDetails cud = (CmmUserDetails) userDetailsService.loadUserByUniqueId(user.getUniqueId());

		log.debug("이메일 주소 : {}", user.getEmail());
		log.debug("비밀번호 : {}", user.getPassword());
		log.debug("이름 : {}", user.getUserName());
		log.debug("권한 : {}", user.getUserRoleList().toString());

		return new UsernamePasswordAuthenticationToken(cud, user.getUniqueId(), cud.getAuthorities());
	}

}
