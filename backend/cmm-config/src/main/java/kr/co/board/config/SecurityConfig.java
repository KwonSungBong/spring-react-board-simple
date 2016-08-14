package kr.co.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by 권 오빈 on 2016. 7. 31..
 */
public class SecurityConfig {

	@Configuration
	@EnableRedisHttpSession
	@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
	@Profile("local")
	protected static class LocalSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
				.anyRequest().permitAll()
				.and()
				.csrf().disable()
				.headers()
				.frameOptions().disable();
		}
	}

}
