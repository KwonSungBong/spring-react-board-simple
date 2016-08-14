package kr.co.board.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.board.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 권성봉 on 8/10/16.
 */
@Slf4j
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationProvider customAuthProvider;

    public StatelessLoginFilter(String defaultFilterProcessesUrl, AuthenticationProvider customAuthProvider) {
        super(defaultFilterProcessesUrl);
        this.customAuthProvider = customAuthProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

        log.debug("StatelessLoginFilter User Email : {}", user.getEmail());

        final UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        return customAuthProvider.authenticate(userToken);
    }
}
