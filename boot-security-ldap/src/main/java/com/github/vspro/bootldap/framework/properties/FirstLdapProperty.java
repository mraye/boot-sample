package com.github.vspro.bootldap.framework.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "ldap.server.first")
public class FirstLdapProperty {

    private String base;

    private String userDn;

    private String password;

    private String url;

    //角色在哪一个节点 RDN
    private String groupSearchBase;

    //查找角色的属性节点，默认是 cn
    //即到groupSearchBase下，查找属性为cn的值
    String groupRoleAttribute = "cn";

    //在角色下查找用户，默认是 uniqueMember={0}
    String groupSearchFilter = "uniqueMember={0}";

    //如果LDAP服务器中指定了前缀，可以不指定，默认加了ROLE_前缀
    String rolePrefix = "";


}
