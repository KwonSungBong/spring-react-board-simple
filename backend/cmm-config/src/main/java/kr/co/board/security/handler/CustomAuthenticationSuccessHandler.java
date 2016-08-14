package kr.co.board.security.handler;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.board.dto.UserDto;
import kr.co.board.security.service.CmmLoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by 권성봉 on 8/10/16.
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Environment environment;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    CustomAuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter){
        this.mapper = messageConverter.getObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = request.getRequestURI();
        log.debug("URL: {}", url);

        if(url != null && url.startsWith("/auth") && url.contains(";")){
            String[] activeProfile = environment.getActiveProfiles();
            log.debug("PROFILES IS PROD ? : {}", Arrays.binarySearch(activeProfile, "prod"));
            log.debug("PROFILES IS SECURITY ? : {}", Arrays.binarySearch(activeProfile, "security"));
            String redirectCode = url.substring(url.indexOf(";") + 1);
            log.debug("REDIRECT_CODE : {}", redirectCode);
            switch (redirectCode){
                case "public" :
                    if(Arrays.binarySearch(activeProfile, "security") > -1) {
                        redirectStrategy.sendRedirect(request, response, "http://localhost:3002/login-success");
                    } else if(Arrays.binarySearch(activeProfile, "prod") > -1) {
                        redirectStrategy.sendRedirect(request, response, "http://www.carlab.co.kr/adm/login-success");
                    }
                    return;
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        PrintWriter writer = response.getWriter();
        String result = mapper.writeValueAsString(modelMapper.map(CmmLoginHelper.getUser(), UserDto.Response.class));
        log.debug("HEADER : {}", response.getHeaderNames());
        log.debug("LOGIN SUCCESS : {}", request);
        writer.append(result);
        writer.flush();
    }
}
