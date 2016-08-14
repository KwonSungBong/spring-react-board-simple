package kr.co.board.security.service;

import kr.co.board.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by 권성봉 on 8/10/16.
 */
@Slf4j
@Component
public class CmmLoginHelper {

    private static Environment environment;

    @Autowired
    public CmmLoginHelper(Environment environment) {
        CmmLoginHelper.environment = environment;
    }

    public static User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (ObjectUtils.isEmpty(authentication) ||
                authentication.getAuthorities().size() == 0 ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {

            String[] activeProfiles = environment.getActiveProfiles();

            User user = new User();

            if(activeProfiles != null){
                if(Arrays.asList(activeProfiles).contains("local") || Arrays.asList(activeProfiles).contains("local-sec") || Arrays.asList(activeProfiles).contains("test")){
                    user.setUniqueId(UUID.fromString("671ab3a1-c22b-45eb-99d0-e10ab7a4742f"));
                }
            }

            return user;

        } else{
            CmmUserDetails cud = (CmmUserDetails) authentication.getPrincipal();
            User user = ((User) cud.getUser());
            return user;
        }
    }

}
