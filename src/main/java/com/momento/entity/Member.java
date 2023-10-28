package com.momento.entity;

import com.momento.constant.Role;
import com.momento.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Members")
@Getter @Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "insta_id", unique = true)
    private String instaId;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToOne(mappedBy = "member")
    private Cart cart;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;




    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setName(memberFormDto.getName());
        member.setNickname(memberFormDto.getNickname());
        member.setPhoneNumber(memberFormDto.getPhoneNumber());
        member.setAddress(memberFormDto.getAddress());
        member.setInstaId(memberFormDto.getInstaId());
//        member.setRole(Role.USER);
        member.setRole(Role.ADMIN);
        return member;
    }

}