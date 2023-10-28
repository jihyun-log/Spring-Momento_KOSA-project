//김지현
//소셜로그인을 했을 때 생성되는 유저들을 저장할 수 있는 엔티티를 생성

package com.momento.entity;

import com.momento.constant.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class SocialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;
    private String providerId;

    @Builder
    public SocialUser(String username, String password, String email, Role role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

}