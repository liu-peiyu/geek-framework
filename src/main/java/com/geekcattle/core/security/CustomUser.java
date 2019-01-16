package com.geekcattle.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author geekcattle
 */
public class CustomUser extends User {

    private String uid;



    public CustomUser(String uid, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(uid,username, password, true, true, true, true, authorities);
    }

    public CustomUser(String uid, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
