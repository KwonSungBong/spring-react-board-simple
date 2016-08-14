package kr.co.board.config;

import kr.co.board.domain.User;
import kr.co.board.domain.UserRole;
import kr.co.board.dto.UserDto;
import kr.co.board.user.impl.UserDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Default User Config
 * Created by 권 오빈 on 2016. 7. 31..
 */
@Configuration
public class DefaultUserConfig {

	@Resource(name = "userDAO")
	private UserDAO userDAO;

	@Autowired
	private ModelMapper modelMapper;

	@Bean
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public InitializingBean initializingBean(){
		return () -> {
			User defaultUser = userDAO.findByEmail("default@carlab.co.kr");
			if(ObjectUtils.isEmpty(defaultUser)){
				UserDto.Create userCreate = new UserDto.Create();
				userCreate.setEmail("default@carlab.co.kr");
				userCreate.setUserName("Default");
				userCreate.setPassword("Default");

				User user = modelMapper.map(userCreate, User.class);
				user.setEnabled(true);

				List<UserRole> userRoleList = new ArrayList<>();
				UserRole userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole("ROLE_ADMIN");
				userRoleList.add(userRole);
				user.setUserRoleList(userRoleList);

				defaultUser = userDAO.save(user);
			}

			System.setProperty("defaultUserUniqueId", defaultUser.getUniqueId().toString());
		};
	}
}
