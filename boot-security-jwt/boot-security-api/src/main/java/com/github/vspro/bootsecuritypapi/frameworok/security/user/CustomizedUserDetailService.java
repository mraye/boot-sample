package com.github.vspro.bootsecuritypapi.frameworok.security.user;

import com.github.vspro.system.persistent.domain.SysUserDo;
import com.github.vspro.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service(value = "customizedUserDetailService")
public class CustomizedUserDetailService implements UserDetailsService {


    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserDo userDo = userService.getUserByUserName(username);
        if (null == userDo) {
            throw new UsernameNotFoundException("用户不存在!!");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        JwtUser user = new JwtUser(userDo.getPassword(), userDo.getUserName(), authorities);
        user.setId(userDo.getId());
        return user;
    }
}
