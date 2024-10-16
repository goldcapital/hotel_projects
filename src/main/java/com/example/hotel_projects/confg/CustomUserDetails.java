package com.example.hotel_projects.confg;




import com.example.hotel_projects.enums.ProfileRole;
import com.example.hotel_projects.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    @Getter
    private String id;
    @Getter
    private String email;
    private String password;
    private Status status;
    private ProfileRole role;

    public CustomUserDetails(String id, String email, String password, Status status, ProfileRole role) {
        this.id = id;
        this.email = email;

        this.password = password;
        this.status = status;
        this.role = role;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new LinkedList<>();
        list.add(new SimpleGrantedAuthority(role.name())); // ROLE
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
            return email;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE);
    }

}
