package org.example.qpin.domain.member.service;

import org.example.qpin.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {

        this.member = member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return member.getRole();    // ROLE_USER, ROLE_ADMIN
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return member.getPassword();
    }

    @Override
    public String getUsername() {

        return member.getName();
    }

    public Long getMemberId() {

        return member.getMemberId();
    }

    // 계정의 만료 여부를 반환합니다. 만료되지 않음을 나타내려면 true를 반환합니다.
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    // 계정이 잠기지 않았는지 여부를 반환합니다. 잠기지 않았음을 나타내려면 true를 반환합니다.
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    // 비밀번호가 만료되지 않았는지 여부를 반환합니다. 만료되지 않았음을 나타내려면 true를 반환합니다.
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    // 계정이 활성화되었는지 여부를 반환합니다. 활성화되었음을 나타내려면 true를 반환합니다.
    @Override
    public boolean isEnabled() {

        return true;
    }
}
